package com.transfers.transfertracker.view.dashboard

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.transfers.transfertracker.enums.Screen
import com.transfers.transfertracker.enums.Screen.SIGN_OUT
import com.transfers.transfertracker.model.news.News
import com.transfers.transfertracker.model.squad.SquadPlayer
import com.transfers.transfertracker.model.stats.FailedToScore
import com.transfers.transfertracker.model.stats.GoalsX
import com.transfers.transfertracker.model.stats.Lineup
import com.transfers.transfertracker.model.stats.TeamStatistics
import com.transfers.transfertracker.model.teams.Team
import com.transfers.transfertracker.navigation.Navigator
import com.transfers.transfertracker.repo.MainRepository
import com.transfers.transfertracker.source.AuthSource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject


class DashboardViewModel @Inject constructor(private val repository: MainRepository,
                                             private val navigator: Navigator,
                                             private val authSource: AuthSource) : ViewModel() {

    private val _shouldSignOut = MutableLiveData<Boolean>()
    val shouldSignOut: LiveData<Boolean> get() = _shouldSignOut

    private val _usersTeams = mutableStateListOf<Team>()
    val usersTeams: MutableList<Team> get() = _usersTeams

    private val _newsList = mutableStateListOf<News>()
    val newsList: MutableList<News> get() = _newsList

    private val _playersList = mutableStateListOf<SquadPlayer>()
    val playersList: MutableList<SquadPlayer> get() = _playersList

    var selectedTeam: MutableState<Team?> = mutableStateOf(null)
    var selectedStory: MutableState<News?> = mutableStateOf(null)
    var shouldShowAddTeamButton = mutableStateOf(false)
    var statistics: MutableState<TeamStatistics?>? = null

    val form = mutableStateOf("Not Provided")
    val lineUp = mutableStateOf("Not Provided")
    val goals = mutableStateOf("Not Provided")
    val failedToScore = mutableStateOf("Not Provided")
    val description = mutableStateOf("These are your team's stats")
    init {
        getCurrentTeam()
        getAllUsersTeams()
    }

    private fun getCurrentTeam() {
        repository.getCurrentTeam()
            .subscribe(
                {
                    fetchTeamNews()
                    fetchPlayers()
                    fetchStatistics()
                    setTeamAsPrimary(it)
                },
                {
                    Log.println(Log.ASSERT, "DashboardViewModel", it.toString())
                }
            )
    }

    private fun getAllUsersTeams() {
        repository.getAllUsersTeam()
            .subscribe(
                {
                    _usersTeams.clear()
                    _usersTeams.addAll(it)
                    shouldShowAddTeamButton.value = _usersTeams.size < MAX_TEAMS
                    removeSelectedTeamFromList()
                },
                {
                    Log.println(Log.ASSERT, "DashboardViewModel", it.toString())
                }
            )
    }

    fun updateCurrentTeam(team: Team) {
        repository.updateCurrentTeam(team)
            .subscribe(
                {
                    fetchTeamNews()
                    fetchPlayers()
                    fetchStatistics()
                    setTeamAsPrimary(team)
                },
                {
                    Log.println(Log.ASSERT, "DashboardViewModel", it.toString())
                }
            )
    }

    fun removeSelectedTeam(team: Team) {
        repository.removeSelectedTeam(team)
            .doOnSuccess {
                getAllUsersTeams()
            }
            .subscribe(
                {
                    navigator.navigateTo(Screen.DASHBOARD)
                },
                {
                    Log.println(Log.ASSERT, "DashboardViewModel", it.toString())
                }
            )
    }

    private fun fetchPlayers() {
        repository.getCurrentTeam()
            .flatMap {
                repository.fetchSquad(it.id.toString())
            }.subscribe(
                {
                    _playersList.clear()
                    _playersList.addAll(it)
                    _playersList.sortByDescending { player ->
                        player.number
                    }
                },
                {
                    Log.println(Log.ASSERT, "DashboardViewModel", it.toString())
                }
            )
    }

    private fun fetchTeamNews() {
        repository.getCurrentTeam()
            .flatMap {
                it.name?.let { name ->
                   return@flatMap repository.fetchLatestTeamNews(name)
                }
                throw Exception("")
            }.subscribe(
                {
                    _newsList.clear()
                    _newsList.addAll(it)
                },
                {
                    Log.println(Log.ASSERT, "DashboardViewModel", it.toString())
                }
            )
    }

    private fun fetchStatistics() {
        repository.getCurrentTeam()
            .flatMap {
                repository.fetchTeamStatistics(it.leagueId.toString(), it.id.toString())
            }.subscribe(
                {
                    formatStatsDescription()
                    formatForm(it.form)
                    formatLineup(it.lineups)
                    formatGoals(it.goals)
                    formatFailedToScore(it.failed_to_score)

                    statistics = mutableStateOf(it)
                },
                {
                    Log.println(Log.ASSERT, "DashboardViewModel", it.toString())
                }
            )
    }

    private fun setTeamAsPrimary(team: Team) {
        selectedTeam.let {
            val current = it.value
            val index = _usersTeams.indexOf(team)
            if(index >= 0 && current != null){
                _usersTeams[index] = current
            }
        }
        selectedTeam.value = team
    }

    private fun removeSelectedTeamFromList(){
        val itr = _usersTeams.iterator()
        while (itr.hasNext()){
            val curr = itr.next()
            selectedTeam.let {
                val team = it.value
                if(team != null && team.id == curr.id){
                    itr.remove()
                }
            }
        }
    }

    private fun formatStatsDescription(){
        description.value = if (selectedTeam.value?.name.isNullOrEmpty()) {
            "These are ${selectedTeam.value?.name}'s stats"
        } else{
            "These are your team's stats"
        }
    }

    private fun formatForm(data: String?) {
        data?.let {
            form.value = it
            if(it.length > 5){
                form.value = it.slice(IntRange(0,4))
            }
        } ?: run {
            form.value = "Not Provided"
        }

    }

    private fun formatLineup(data: List<Lineup>?){
        if(data.isNullOrEmpty() || data[0].formation.isNullOrEmpty()){
            lineUp.value = "Not Provided"
        }
        else{
            lineUp.value = data[0].formation!!
        }
    }

    private fun formatGoals(data: GoalsX?){
        if(data?.`for`?.total?.total == null){
            goals.value = "Not Provided"
        }
        else{
            goals.value = data.`for`.total.total.toString()
        }
    }

    private fun formatFailedToScore(data: FailedToScore?){
        if(data?.total == null){
            failedToScore.value = "Not Provided"
        }
        else{
            failedToScore.value = data.total.toString()
        }
    }

    fun navigateToAddTeam() {
        navigator.navigateTo(Screen.COUNTRY_LIST)
    }

    fun navigateToPlayersList() {
        navigator.navigateTo(Screen.PLAYER_LIST)
    }

    fun navigateToPlayerProfile() {
        navigator.navigateTo(Screen.PLAYER_PROFILE)
    }

    fun navigateToTeamProfile() {
        navigator.navigateTo(Screen.TEAM_PROFILE)
    }

    fun navigateToNewsList() {
        navigator.navigateTo(Screen.NEWS_LIST)
    }

    fun navigateToArticle(link: String) {
        navigator.navigateTo(Screen.NEWS_WEB_VIEW)
    }

    fun signOut() {
        authSource.signOut()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    navigator.navigateTo(SIGN_OUT)
                    Log.println(Log.ASSERT, "sign out", "sign out")
                },
                {

                }
            )
    }

    private companion object{
        const val MAX_TEAMS = 5
    }
}