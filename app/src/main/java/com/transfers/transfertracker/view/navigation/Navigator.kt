package com.transfers.transfertracker.view.navigation

import com.transfers.transfertracker.enums.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class Navigator {
    private val _sharedFlow = MutableSharedFlow<Screen>(extraBufferCapacity = 1)
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun navigateTo(navTarget: Screen) {
        _sharedFlow.tryEmit(navTarget)
    }
}