package com.transfers.transfertracker.view.transfer

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import com.transfers.transfertracker.R
import com.transfers.transfertracker.enums.EScreen
import com.transfers.transfertracker.model.tranfers.PlayerTransfer
import com.transfers.transfertracker.navigation.Navigator
import com.transfers.transfertracker.repo.MainRepository
import com.transfers.transfertracker.util.SubscribeOnLifecycle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
class TransferListViewModel @Inject constructor(
    private val repository: MainRepository,
    private val navigator: Navigator
): ViewModel(), DefaultLifecycleObserver, SubscribeOnLifecycle {
    private val compositeDisposable = CompositeDisposable()

    private val _transferList = mutableStateListOf<PlayerTransfer>()
    val transferList: List<PlayerTransfer> get() = _transferList

    val teamId = mutableStateOf("")
    val leagueId = mutableStateOf("")

    val shouldShowErrorDialog = mutableStateOf(false)
    val errorTitle = mutableStateOf(R.string.title_generic_error)
    val errorMessage = mutableStateOf(R.string.message_find_players_error)

    init {
        fetchTransfers()
    }

    fun onDestroy() {
        compositeDisposable.dispose()
    }

    override fun subscribeOnLifecycle(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    private fun fetchTransfers() {
        subscribeOnLifecycle(
            repository.getCurrentTeam()
                .flatMap { repository.fetchTransfers(it.id.toString()) }.subscribe(
                    {
                        if(it.isNotEmpty()){
                            _transferList.clear()
                            _transferList.addAll(it)
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

    fun navigateToPlayerProfile(player : String, teamId: String, leagueId: String){
        navigator.navigateTo("${EScreen.PLAYER_PROFILE}/$player/${teamId}/${leagueId}")
    }
}