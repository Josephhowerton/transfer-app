package com.transfers.transfertracker.view.player

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.transfers.transfertracker.R
import com.transfers.transfertracker.enums.EScreen
import com.transfers.transfertracker.model.squad.SquadPlayer
import com.transfers.transfertracker.navigation.Navigator
import com.transfers.transfertracker.repo.MainRepository
import com.transfers.transfertracker.util.SubscribeOnLifecycle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
class PlayersListViewModel @Inject constructor(
    private val repository: MainRepository,
    private val navigator: Navigator
): ViewModel(), SubscribeOnLifecycle {
    private val compositeDisposable = CompositeDisposable()

    private val _playersList = mutableStateListOf<SquadPlayer>()
    val playersList: List<SquadPlayer> get() = _playersList

    val teamId = mutableStateOf("")
    val leagueId = mutableStateOf("")

    val shouldShowErrorDialog = mutableStateOf(false)
    val errorTitle = mutableStateOf(R.string.title_generic_error)
    val errorMessage = mutableStateOf(R.string.message_find_players_error)

    fun onDestroy() {
        compositeDisposable.dispose()
    }

    override fun subscribeOnLifecycle(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun fetchSquad() {
       subscribeOnLifecycle(
           repository.getCurrentTeam()
               .flatMap {
                   if(it.id == null || it.leagueId == null) throw Exception()
                   this.teamId.value = it.id.toString()
                   this.leagueId.value = it.leagueId.toString()
                   repository.fetchSquad(it.id.toString())
               }.subscribe(
                   {
                       if(it.isNotEmpty()){
                           _playersList.clear()
                           _playersList.addAll(it)
                           _playersList.sortByDescending { player ->
                               player.number
                           }
                       }else{
                           shouldShowErrorDialog.value = true
                       }
                   },
                   {
                       shouldShowErrorDialog.value = true
                   }
               )
       )
    }

    fun navigateToPlayerProfile(player : String){
        navigator.navigateTo("${EScreen.PLAYER_PROFILE}/$player/${teamId.value}/${leagueId.value}")
    }
}