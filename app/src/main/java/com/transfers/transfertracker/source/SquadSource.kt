package com.transfers.transfertracker.source

import com.transfers.transfertracker.model.player.Player
import com.transfers.transfertracker.model.squad.SquadResponse
import io.reactivex.rxjava3.core.Single

interface SquadSource {
    fun fetchSquad(tid: String): Single<List<Player>>
    fun fetchSquadFromApi(tid: String): Single<SquadResponse>
}