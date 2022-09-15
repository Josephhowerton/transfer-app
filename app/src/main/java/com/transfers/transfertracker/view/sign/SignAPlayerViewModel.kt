package com.transfers.transfertracker.view.sign

import androidx.lifecycle.ViewModel
import com.transfers.transfertracker.navigation.Navigator
import com.transfers.transfertracker.repo.MainRepository
import javax.inject.Inject

class SignAPlayerViewModel @Inject constructor(
    private val repository: MainRepository,
    private val navigator: Navigator
): ViewModel() {
}