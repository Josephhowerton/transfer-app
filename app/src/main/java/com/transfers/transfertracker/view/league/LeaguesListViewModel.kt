package com.transfers.transfertracker.view.league

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.transfers.transfertracker.R
import com.transfers.transfertracker.enums.EScreen
import com.transfers.transfertracker.model.league.League
import com.transfers.transfertracker.navigation.Navigator
import com.transfers.transfertracker.repo.MainRepository
import com.transfers.transfertracker.util.SubscribeOnLifecycle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
class LeaguesListViewModel @Inject constructor(
    private val repository: MainRepository,
    private val navigator: Navigator
): ViewModel(), SubscribeOnLifecycle {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val _leaguesList = mutableStateListOf<League>()
    val leaguesList: List<League> get() = _leaguesList

    val shouldShowErrorDialog = mutableStateOf(false)
    val errorTitle = mutableStateOf(R.string.title_generic_error)
    val errorMessage = mutableStateOf(R.string.message_find_countries_error)

    fun onDestroy() {
        compositeDisposable.dispose()
    }

    override fun subscribeOnLifecycle(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun fetchLeaguesByCountry(country: String) {
        subscribeOnLifecycle(repository.fetchLeaguesByCountry(country)
            .subscribe(
                {
                    if(it.isNotEmpty()){
                        _leaguesList.clear()
                        _leaguesList.addAll(it)
                    }else{
                        shouldShowErrorDialog.value = true
                    }
                },
                {
                    shouldShowErrorDialog.value = true
                }
            ))
    }

    fun navigateToTeamList(teamId: Int){
        navigator.navigateTo("${EScreen.TEAM_LIST}/$teamId")
    }
}