package com.transfers.transfertracker.source.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.transfers.transfertracker.model.player.Player
import com.transfers.transfertracker.model.squad.SquadResponse
import com.transfers.transfertracker.network.SquadEndpoints.KEY_TEAM
import com.transfers.transfertracker.network.SquadService
import com.transfers.transfertracker.source.SquadSource
import com.transfers.transfertracker.util.Keys.API_FOOTBALL_HEADER_MAP
import com.transfers.transfertracker.util.RemoteCache
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SquadDataSourceImpl @Inject constructor(private val squadService: SquadService) : SquadSource {

    private val squadsMap: MutableMap<String, String> = mutableMapOf()

    override fun fetchSquad(tid: String): Single<List<Player>> =
        fetchSquadFromApi(tid)
            .flatMap { assemblePlayerList(tid, it) }
            .doOnSuccess { players ->
                players.forEach { player ->
                    savePlayers(player)
                }
            }

    override fun fetchSquadFromApi(tid: String): Single<SquadResponse> {
        squadsMap[KEY_TEAM] = tid
        return squadService.fetchSquad(API_FOOTBALL_HEADER_MAP, squadsMap)
    }

    private fun savePlayers(player: Player) {
        FirebaseFirestore.getInstance()
            .collection(RemoteCache.COLLECTION_PLAYERS)
            .document(player.id.toString()).set(player)
            .addOnSuccessListener { }
            .addOnFailureListener { }
    }

    private fun assemblePlayerList(tid: String, data: SquadResponse): Single<List<Player>> =
        Single.create { emitter ->
            val players = mutableListOf<Player>()
            for (response in data.response) {
                response.players.forEach {
                    it.teamId = tid
                    players.add(it)
                }
            }
            emitter.onSuccess(players)
        }
}