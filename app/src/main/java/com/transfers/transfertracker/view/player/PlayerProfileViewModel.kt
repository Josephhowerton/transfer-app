package com.transfers.transfertracker.view.player

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.transfers.transfertracker.model.player.Player
import com.transfers.transfertracker.model.player.PlayerProfile
import com.transfers.transfertracker.model.player.PlayerStatistic
import com.transfers.transfertracker.navigation.Navigator
import com.transfers.transfertracker.repo.MainRepository
import javax.inject.Inject

class PlayerProfileViewModel @Inject constructor(
    private val repository: MainRepository,
    private val navigator: Navigator
): ViewModel() {


    val playerInfo: MutableState<Player>? = null
    val playerStatistic: MutableState<PlayerStatistic>? = null
    val playerCountryFlag = mutableStateOf("")

}