package com.transfers.transfertracker.source.impl

import com.transfers.transfertracker.enums.ETransfer
import com.transfers.transfertracker.model.player.PlayerProfile
import com.transfers.transfertracker.model.squad.SquadPlayer
import com.transfers.transfertracker.model.squad.SquadResponse
import com.transfers.transfertracker.model.tranfers.PlayerTransfer
import com.transfers.transfertracker.model.tranfers.Transfer
import com.transfers.transfertracker.model.tranfers.TransferData
import com.transfers.transfertracker.model.tranfers.TransferResponse
import com.transfers.transfertracker.network.SquadEndpoints.KEY_TEAM
import com.transfers.transfertracker.network.service.SquadService
import com.transfers.transfertracker.network.TeamsEndpoints.KEY_ID
import com.transfers.transfertracker.network.TeamsEndpoints.KEY_LEAGUE
import com.transfers.transfertracker.network.TeamsEndpoints.KEY_SEASON
import com.transfers.transfertracker.source.SquadSource
import com.transfers.transfertracker.util.Keys.API_FOOTBALL_HEADER_MAP
import io.reactivex.rxjava3.core.Single
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class SquadDataSourceImpl @Inject constructor(private val squadService: SquadService) :
    SquadSource {

    private var squadsMap: MutableMap<String, String> = mutableMapOf()

    override fun fetchSquad(tid: String): Single<List<SquadPlayer>> {
        squadsMap = mutableMapOf(KEY_TEAM to tid)
        return squadService.fetchSquad(API_FOOTBALL_HEADER_MAP, squadsMap)
            .concatMap { assemblePlayerList(it) }
    }

    override fun fetchTransfers(tid: String): Single<List<PlayerTransfer>> {
        squadsMap = mutableMapOf(KEY_TEAM to tid)
        return squadService.fetchTransfers(API_FOOTBALL_HEADER_MAP, squadsMap)
            .concatMap { filterTransfers(tid, it) }
    }

    override fun fetchPlayerProfiles(leagueId: String, teamId: String): Single<List<PlayerProfile>> {
        squadsMap = mutableMapOf(
            KEY_TEAM to teamId,
            KEY_LEAGUE to leagueId,
            KEY_SEASON to "2022"
        )

        return squadService.fetchPlayerProfile(API_FOOTBALL_HEADER_MAP, squadsMap)
            .map { data ->
                data.response?.let {
                    return@map it
                }
                throw Exception("Response is null")
            }
    }

    override fun fetchPlayerProfile(leagueId: String, teamId: String, playerId: String): Single<PlayerProfile> {

        squadsMap = mutableMapOf(
            KEY_TEAM to teamId,
            KEY_LEAGUE to leagueId,
            KEY_ID to playerId,
            KEY_SEASON to "2022"
        )

        return squadService.fetchPlayerProfile(API_FOOTBALL_HEADER_MAP, squadsMap)
            .map { data ->
                data.response?.let {
                    if(it.isNotEmpty()){
                        return@map it[0]
                    }
                }
                throw Exception("Response is null")
            }
    }

    override fun fetchPlayerProfile(teamId: String, playerId: String): Single<PlayerProfile> {

        squadsMap = mutableMapOf(
            KEY_TEAM to teamId,
            KEY_ID to playerId,
            KEY_SEASON to "2022"
        )

        return squadService.fetchPlayerProfile(API_FOOTBALL_HEADER_MAP, squadsMap)
            .map { data ->
                data.response?.let {
                    if(it.isNotEmpty()){
                        return@map it[0]
                    }
                }
                throw Exception("Response is null")
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

    private fun filterTransfers(teamId: String, response: TransferResponse): Single<List<PlayerTransfer>> =
        Single.create { emitter ->
            val transferList = mutableListOf<PlayerTransfer>()
            val responseList = response.response
            if(responseList.isNotEmpty()){
                responseList.forEach { transferData ->
                    for(it in transferData.transfers){
                        if(it.date != null){
                            if(compareTransferDate(it.date)){
                                transferList.add(buildPlayerTransfer(teamId, it, transferData))
                                break
                            }
                        }
                    }
                }
            } else{
                emitter.onError(Exception("Response is Empty"))
            }
            emitter.onSuccess(transferList)
        }

    private fun buildPlayerTransfer(teamId: String, transfer: Transfer, transferData: TransferData): PlayerTransfer {
        if(transferData.player != null){
            val teams = transfer.teams
            if(teams != null) {
                if (teams.`in`?.id == teamId.toInt()) {
                    transferData.player.teamId = teamId
                    transferData.player.transfer = ETransfer.IN
                } else {
                    transferData.player.teamId = teams.`in`?.id?.toString()
                    transferData.player.transfer = ETransfer.OUT
                }
            }

            if(transferData.player.id != null){
                transferData.player.photo = "https://media.api-sports.io/football/players/${transferData.player.id}.png"
            }
            return PlayerTransfer(
                id = transferData.player.id,
                name = transferData.player.name,
                teamId = transferData.player.teamId,
                photo = transferData.player.photo,
                transfer = transferData.player.transfer,
                type = transfer.type
            )
        }

        throw Exception("")
    }


    private fun compareTransferDate(date: String): Boolean{
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val currentDate = LocalDate.now()
        val transferDate = LocalDate.parse(date, formatter)
        val period = Period.between(transferDate, currentDate)
        if ( period.years >= 1 || period.years < 0 || (period.months > 6 || period.months < 0))
            return false
        return true
    }
}