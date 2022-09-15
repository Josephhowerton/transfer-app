package com.transfers.transfertracker.source

import com.transfers.transfertracker.model.player.PlayerProfile
import com.transfers.transfertracker.model.squad.SquadPlayer
import com.transfers.transfertracker.model.squad.SquadResponse
import io.reactivex.rxjava3.core.Single

interface SquadSource {
    fun fetchSquad(tid: String): Single<List<SquadPlayer>>
    fun fetchPlayerProfile(leagueId: String, teamId: String, playerId: String): Single<List<PlayerProfile>>
}