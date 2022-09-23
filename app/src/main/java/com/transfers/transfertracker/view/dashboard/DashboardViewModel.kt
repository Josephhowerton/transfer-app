package com.transfers.transfertracker.view.dashboard

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.transfers.transfertracker.enums.EScreen
import com.transfers.transfertracker.enums.EScreen.SIGN_OUT
import com.transfers.transfertracker.model.news.News
import com.transfers.transfertracker.model.squad.SquadPlayer
import com.transfers.transfertracker.model.stats.FailedToScore
import com.transfers.transfertracker.model.stats.GoalsX
import com.transfers.transfertracker.model.stats.Lineup
import com.transfers.transfertracker.model.stats.TeamStatistics
import com.transfers.transfertracker.model.teams.Team
import com.transfers.transfertracker.model.tranfers.PlayerTransfer
import com.transfers.transfertracker.navigation.Navigator
import com.transfers.transfertracker.repo.MainRepository
import com.transfers.transfertracker.source.AuthSource
import com.transfers.transfertracker.util.SubscribeOnLifecycle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: MainRepository,
    private val navigator: Navigator,
    private val authSource: AuthSource
) : ViewModel(), SubscribeOnLifecycle {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val _usersTeams = mutableStateListOf<Team>()
    val usersTeams: MutableList<Team> get() = _usersTeams

    private val _newsList = mutableStateListOf<News>()
    val newsList: MutableList<News> get() = _newsList

    private val _playersList = mutableStateListOf<SquadPlayer>()
    val playersList: MutableList<SquadPlayer> get() = _playersList

    private val _transferList = mutableStateListOf<PlayerTransfer>()
    val transferList: MutableList<PlayerTransfer> get() = _transferList

    var selectedTeam: MutableState<Team?> = mutableStateOf(null)
    var shouldShowAddTeamButton = mutableStateOf(false)
    var statistics: MutableState<TeamStatistics?>? = null

    val form = mutableStateOf("Not Provided")
    val lineUp = mutableStateOf("Not Provided")
    val goals = mutableStateOf("Not Provided")
    val failedToScore = mutableStateOf("Not Provided")
    val description = mutableStateOf("These are your team's stats")

    init {
        initialize()
    }

    fun onDestroy() {
        compositeDisposable.dispose()
    }

    override fun subscribeOnLifecycle(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    private fun initialize(){
        subscribeOnLifecycle(
            repository.getCurrentTeam()
                .subscribe(
                    {
                        getAllUsersTeams()
                        fetchTeamNews()
                        fetchPlayers()
                        fetchStatistics()
                        fetchTransfers()
                        setTeamAsPrimary(it)
                    },
                    {}
                ))
    }

    private fun getAllUsersTeams() {
        subscribeOnLifecycle(
            repository.getAllUsersTeam()
                .subscribe(
                    {
                        removeSelectedTeamFromList(it)
                        shouldShowAddTeamButton.value = _usersTeams.size < MAX_TEAMS
                    },
                    {}
                )
        )
    }

    fun updateCurrentTeam(team: Team) {
        subscribeOnLifecycle(repository.updateCurrentTeam(team)
            .subscribe(
                {
                    fetchTeamNews()
                    fetchPlayers()
                    fetchStatistics()
                    fetchTransfers()
                    setTeamAsPrimary(team)
                },
                {}
            ))
    }

    fun removeSelectedTeam(team: Team) {
        subscribeOnLifecycle(
            repository.removeSelectedTeam(team)
                .doOnSuccess {
                    getAllUsersTeams()
                }
                .subscribe(
                    {
                        navigator.navigateTo(EScreen.DASHBOARD)
                    },
                    {}
                )
        )
    }

    private fun fetchPlayers() {
        subscribeOnLifecycle(
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
                    {}
                )
        )
    }

    private fun fetchTransfers() {
        subscribeOnLifecycle(
            repository.getCurrentTeam()
                .flatMap { repository.fetchTransfers(it.id.toString()) }.subscribe(
                    {
                        _transferList.clear()
                        _transferList.addAll(it)
                    },
                    {}
                )
        )
    }

    private fun fetchTeamNews() {
        subscribeOnLifecycle(
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
                    {}
                )
        )
    }

    private fun fetchStatistics() {
        subscribeOnLifecycle(
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
                    {}
                )
        )
    }

    private fun setTeamAsPrimary(team: Team) {
        selectedTeam.let {
            val current = it.value
            val index = _usersTeams.indexOf(team)
            if (index >= 0 && current != null) {
                _usersTeams[index] = current
            }
        }
        selectedTeam.value = team
    }

    private fun removeSelectedTeamFromList(list: List<Team>){
        val itr = list.toMutableList().iterator()
        _usersTeams.clear()
        while (itr.hasNext()){
            val curr = itr.next()
            selectedTeam.let {
                val team = it.value
                if(team != null && team.id != curr.id){
                    itr.remove()
                    _usersTeams.add(curr)
                }
            }
        }
    }

    private fun formatStatsDescription() {
        description.value = if (selectedTeam.value?.name.isNullOrEmpty()) {
            "These are ${selectedTeam.value?.name}'s stats"
        } else {
            "These are your team's stats"
        }
    }

    private fun formatForm(data: String?) {
        data?.let {
            form.value = it
            if (it.length > 5) {
                form.value = it.slice(IntRange(0, 4))
            }
        } ?: run {
            form.value = "Not Provided"
        }

    }

    private fun formatLineup(data: List<Lineup>?) {
        if (data.isNullOrEmpty() || data[0].formation.isNullOrEmpty()) {
            lineUp.value = "Not Provided"
        } else {
            lineUp.value = data[0].formation!!
        }
    }

    private fun formatGoals(data: GoalsX?) {
        if (data?.`for`?.total?.total == null) {
            goals.value = "Not Provided"
        } else {
            goals.value = data.`for`.total.total.toString()
        }
    }

    private fun formatFailedToScore(data: FailedToScore?) {
        if (data?.total == null) {
            failedToScore.value = "Not Provided"
        } else {
            failedToScore.value = data.total.toString()
        }
    }

    fun navigateToAddTeam() {
        navigator.navigateTo(EScreen.COUNTRY_LIST)
    }

    fun navigateToSquadList() {
        navigator.navigateTo(EScreen.SQUAD_LIST)
    }

    fun navigateToTransferList() {
        navigator.navigateTo(EScreen.TRANSFER_LIST)
    }

    fun navigateToPlayerProfile(player: String, team: String, league: String?) {
        navigator.navigateTo("${EScreen.PLAYER_PROFILE}/$player/$team/$league")
    }

    fun navigateToTeamProfile(team: String, league: String) {
        navigator.navigateTo("${EScreen.TEAM_PROFILE}/$team/$league")
    }

    fun navigateToNewsList() {
        navigator.navigateTo(EScreen.NEWS_LIST)
    }

    fun navigateToArticle(link: String) {
        val encoded = URLEncoder.encode(link, StandardCharsets.UTF_8.toString())
        navigator.navigateTo("${EScreen.NEWS_WEB_VIEW}/$encoded")
    }

    fun signOut(): Disposable = authSource.signOut()
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            {
                navigator.navigateTo(SIGN_OUT)
            },
            {
                navigator.navigateTo(SIGN_OUT)
            }
        )

    private companion object {
        const val MAX_TEAMS = 5
    }
}