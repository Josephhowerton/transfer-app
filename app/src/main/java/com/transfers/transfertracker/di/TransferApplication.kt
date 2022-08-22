package com.transfers.transfertracker.di

import android.app.Application
import com.google.firebase.FirebaseApp
import com.transfers.transfertracker.di.components.AppComponent
import com.transfers.transfertracker.di.components.DaggerAppComponent

class TransferApplication: Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}