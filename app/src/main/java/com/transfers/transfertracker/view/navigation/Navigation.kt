package com.transfers.transfertracker.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.transfers.transfertracker.enums.Screen
import com.transfers.transfertracker.view.auth.ForgotPassword
import com.transfers.transfertracker.view.auth.SignIn
import com.transfers.transfertracker.view.auth.SignUp
import com.transfers.transfertracker.view.auth.viewmodel.AuthViewModel
import com.transfers.transfertracker.view.list.CountryList
import com.transfers.transfertracker.view.list.LeaguesList
import com.transfers.transfertracker.view.list.PlayersList
import com.transfers.transfertracker.view.list.TeamsList
import com.transfers.transfertracker.view.main.screens.Dashboard
import com.transfers.transfertracker.view.main.screens.DashboardScreen
import com.transfers.transfertracker.view.main.viewmodel.DashboardViewModel
import com.transfers.transfertracker.view.player.PlayerDetail
import com.transfers.transfertracker.view.player.PlayerDetailViewModel
import com.transfers.transfertracker.view.stats.InDepthStatistics
import com.transfers.transfertracker.view.stats.TeamStatistics
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun AuthNavGraph(navController: NavHostController = rememberNavController(), viewModel: AuthViewModel) {
    NavHost(navController = navController, startDestination = Screen.SIGN_IN.name) {
        composable(Screen.SIGN_IN.name) { SignIn(viewModel, navController) }
        composable(Screen.SIGN_UP.name) { SignUp(viewModel) }
        composable(Screen.FORGOT_PASSWORD.name) { ForgotPassword(viewModel) }
    }
}

@Composable
fun DashboardNavGraph(navController: NavHostController = rememberNavController(),
                      navigator: Navigator,
                      viewModel: DashboardViewModel) {
    LaunchedEffect("navigation") {
        navigator.sharedFlow.onEach {
            navController.navigate(it.name)
        }.launchIn(this)
    }

    NavHost(navController = navController, startDestination = Screen.DASHBOARD.name) {
        composable(Screen.DASHBOARD.name) { Dashboard(viewModel) }
        composable(Screen.PLAYER_DETAILS.name) { PlayerDetail(viewModel) }
        composable(Screen.COUNTRY_LIST.name) { CountryList(dashboardViewModel = viewModel) }
        composable(Screen.LEAGUE_LIST.name) { LeaguesList(dashboardViewModel = viewModel) }
        composable(Screen.TEAM_LIST.name) { TeamsList(dashboardViewModel = viewModel) }
        composable(Screen.DASHBOARD.name) { Dashboard(viewModel) }
        composable(Screen.PLAYER_LIST.name) { PlayersList() }
        composable(Screen.PLAYER_DETAILS.name) { PlayerDetail(viewModel) }
        composable(Screen.TEAM_STATISTICS.name) { TeamStatistics(viewModel) }
        composable(Screen.STATISTIC_DETAILS.name) { InDepthStatistics(viewModel) }
    }
}
