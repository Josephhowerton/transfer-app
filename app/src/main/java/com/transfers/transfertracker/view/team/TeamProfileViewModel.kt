package com.transfers.transfertracker.view.team

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.transfers.transfertracker.model.stats.TeamStatistics
import com.transfers.transfertracker.model.teams.Team
import com.transfers.transfertracker.navigation.Navigator
import com.transfers.transfertracker.repo.MainRepository
import javax.inject.Inject

class TeamProfileViewModel @Inject constructor(
    private val repository: MainRepository,
    private val navigator: Navigator
): ViewModel() {
    var statistics: MutableState<TeamStatistics>? = null
    var selectedTeam: MutableState<Team>? = null
}