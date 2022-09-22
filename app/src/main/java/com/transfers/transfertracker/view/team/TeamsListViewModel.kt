package com.transfers.transfertracker.view.team

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.transfers.transfertracker.R
import com.transfers.transfertracker.enums.EScreen
import com.transfers.transfertracker.model.teams.Team
import com.transfers.transfertracker.navigation.Navigator
import com.transfers.transfertracker.repo.MainRepository
import com.transfers.transfertracker.util.SubscribeOnLifecycle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
class TeamsListViewModel @Inject constructor(
    private val repository: MainRepository,
    private val navigator: Navigator
): ViewModel(), SubscribeOnLifecycle {
    private val compositeDisposable = CompositeDisposable()

    private val _teamsList = mutableStateListOf<Team>()
    val teamsList: List<Team> get() = _teamsList

    val shouldShowErrorDialog = mutableStateOf(false)
    val errorTitle = mutableStateOf(R.string.title_generic_error)
    val errorMessage = mutableStateOf(R.string.message_generic_error)

    fun onDestroy() {
        compositeDisposable.dispose()
    }


    override fun subscribeOnLifecycle(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun fetchTeamsByLeague(leagueId: String) {
        subscribeOnLifecycle(
            repository.fetchTeams(leagueId)
                .subscribe(
                    {
                        if(it.isNotEmpty()){
                            _teamsList.clear()
                            _teamsList.addAll(it)
                        }else{
                            errorTitle.value = R.string.title_find_team_error
                            errorMessage.value = R.string.message_find_team_error
                        }
                    },
                    {
                        shouldShowErrorDialog.value = true
                    }
                )
        )
    }

    fun saveSelectedTeam(team: Team) {
        subscribeOnLifecycle(
            repository.saveSelectedTeam(team)
                .subscribe(
                    {
                        navigator.navigateTo(EScreen.DASHBOARD)
                    },
                    {
                        errorTitle.value = R.string.title_add_team_error
                        errorMessage.value = R.string.message_add_team_error
                        shouldShowErrorDialog.value = true
                    }
                )
        )
    }
}