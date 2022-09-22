package com.transfers.transfertracker.view.sign

import androidx.lifecycle.ViewModel
import com.transfers.transfertracker.navigation.Navigator
import com.transfers.transfertracker.repo.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignAPlayerViewModel @Inject constructor(
    private val repository: MainRepository,
    private val navigator: Navigator
): ViewModel() {
}