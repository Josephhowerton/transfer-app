package com.transfers.transfertracker.view.player

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.transfers.transfertracker.model.squad.SquadPlayer
import com.transfers.transfertracker.navigation.Navigator
import com.transfers.transfertracker.repo.MainRepository
import javax.inject.Inject

class PlayersListViewModel @Inject constructor(
    private val repository: MainRepository,
    private val navigator: Navigator
): ViewModel() {
    private val _playersList = mutableStateListOf<SquadPlayer>()
    val playersList: List<SquadPlayer> get() = _playersList

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
}