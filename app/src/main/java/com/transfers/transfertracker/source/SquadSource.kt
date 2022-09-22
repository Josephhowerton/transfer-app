package com.transfers.transfertracker.source

import com.transfers.transfertracker.model.player.PlayerProfile
import com.transfers.transfertracker.model.squad.SquadPlayer
import com.transfers.transfertracker.model.squad.SquadResponse
import com.transfers.transfertracker.model.tranfers.PlayerTransfer
import com.transfers.transfertracker.model.tranfers.Transfer
import com.transfers.transfertracker.model.tranfers.TransferData
import io.reactivex.rxjava3.core.Single

interface SquadSource {
    fun fetchSquad(tid: String): Single<List<SquadPlayer>>
    fun fetchTransfers(tid: String): Single<List<PlayerTransfer>>
    fun fetchPlayerProfiles(leagueId: String, teamId: String): Single<List<PlayerProfile>>
    fun fetchPlayerProfile(leagueId: String, teamId: String, playerId: String): Single<PlayerProfile>
    fun fetchPlayerProfile(teamId: String, playerId: String): Single<PlayerProfile>
}