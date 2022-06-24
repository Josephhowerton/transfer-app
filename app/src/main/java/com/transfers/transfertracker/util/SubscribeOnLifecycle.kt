package com.transfers.transfertracker.util

import io.reactivex.rxjava3.disposables.Disposable

interface SubscribeOnLifecycle {
    fun subscribeOnLifecycle(disposable: Disposable)
}