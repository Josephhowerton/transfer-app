package com.transfers.transfertracker.source.impl

import android.util.Log
import com.transfers.transfertracker.model.player.PlayerProfile
import com.transfers.transfertracker.model.squad.SquadPlayer
import com.transfers.transfertracker.model.squad.SquadResponse
import com.transfers.transfertracker.network.SquadEndpoints.KEY_TEAM
import com.transfers.transfertracker.network.SquadService
import com.transfers.transfertracker.network.TeamsEndpoints.KEY_LEAGUE
import com.transfers.transfertracker.network.TeamsEndpoints.KEY_SEASON
import com.transfers.transfertracker.network.TeamsEndpoints.KEY_ID
import com.transfers.transfertracker.source.SquadSource
import com.transfers.transfertracker.util.Keys.API_FOOTBALL_HEADER_MAP
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SquadDataSourceImpl @Inject constructor(private val squadService: SquadService) :
    SquadSource {

    private var squadsMap: MutableMap<String, String> = mutableMapOf()

    override fun fetchSquad(tid: String): Single<List<SquadPlayer>> {
        squadsMap = mutableMapOf(KEY_TEAM to tid)
        return squadService.fetchSquad(API_FOOTBALL_HEADER_MAP, squadsMap)
            .concatMap { assemblePlayerList(it) }
            .doOnError {
                Log.println(Log.ASSERT, "fetchSquad", it.toString())
            }
    }

    override fun fetchPlayerProfile(
        leagueId: String,
        teamId: String,
        playerId: String
    ): Single<List<PlayerProfile>> {
        squadsMap = mutableMapOf(
            KEY_TEAM to teamId,
            KEY_LEAGUE to leagueId,
            KEY_ID to playerId,
            KEY_SEASON to "2022"
        )

        return squadService.fetchPlayerProfile(API_FOOTBALL_HEADER_MAP, squadsMap)
            .map { data ->
                data.response?.let {
                    return@map it
                }
                throw Exception("Response is null")
            }.doOnError {
                Log.println(Log.ASSERT, "fetchSquad", it.toString())
            }
    }

    private fun assemblePlayerList(data: SquadResponse): Single<List<SquadPlayer>> =
        Single.create { emitter ->
            data.response?.let {
                val squadPlayers = mutableListOf<SquadPlayer>()
                for (response in data.response) {
                    val team = response.team
                    val players = response.players
                    if (players != null && team != null && players.isNotEmpty()) {
                        response.players.forEach {
                            if(team.id != null){
                                it.teamId = team.id.toString()
                                squadPlayers.add(it)
                            }
                        }
                    }else{
                        emitter.onError(Exception("Could not assemble player list"))
                    }
                }
                emitter.onSuccess(squadPlayers)
            } ?: emitter.onError(Exception("Could not assemble player list"))
        }
}