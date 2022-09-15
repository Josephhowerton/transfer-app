package com.transfers.transfertracker.di

import android.app.Application
import com.google.firebase.FirebaseApp
import com.transfers.transfertracker.di.components.*
import com.transfers.transfertracker.view.team.TeamsListViewModel

class TransferApplication: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    val authComponent: AuthComponent by lazy {
        DaggerAuthComponent.factory().create(this)
    }

    val countryListComponent: CountryListComponent by lazy {
        DaggerCountryListComponent.factory().create(this)
    }

    val dashboardComponent: DashboardComponent by lazy {
        DaggerDashboardComponent.factory().create(this)
    }

    val leaguesListComponent: LeaguesListComponent by lazy {
        DaggerLeaguesListComponent.factory().create(this)
    }

    val newsComponent: NewsComponent by lazy {
        DaggerNewsComponent.factory().create(this)
    }

    val playerProfileComponent: PlayerProfileComponent by lazy {
        DaggerPlayerProfileComponent.factory().create(this)
    }

    val playersListComponent: PlayersListComponent by lazy {
        DaggerPlayersListComponent.factory().create(this)
    }

    val teamListComponent: TeamListComponent by lazy {
        DaggerTeamListComponent.factory().create(this)
    }

    val teamProfileComponent: TeamProfileComponent by lazy {
        DaggerTeamProfileComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}