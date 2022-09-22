package com.transfers.transfertracker.source

import com.transfers.transfertracker.model.teams.Team
import io.reactivex.rxjava3.core.Single

interface TeamsSource {
    fun fetchTeams(id: String) : Single<List<Team>>
}