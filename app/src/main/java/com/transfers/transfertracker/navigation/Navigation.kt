package com.transfers.transfertracker.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.transfers.transfertracker.enums.EScreen
import com.transfers.transfertracker.view.auth.ForgotPassword
import com.transfers.transfertracker.view.auth.SignIn
import com.transfers.transfertracker.view.auth.SignUp
import com.transfers.transfertracker.view.auth.viewmodel.AuthViewModel
import com.transfers.transfertracker.view.components.NewsScreen
import com.transfers.transfertracker.view.country.CountryList
import com.transfers.transfertracker.view.dashboard.Dashboard
import com.transfers.transfertracker.view.league.LeaguesList
import com.transfers.transfertracker.view.news.NewsListScreen
import com.transfers.transfertracker.view.player.PlayerProfile
import com.transfers.transfertracker.view.player.SquadList
import com.transfers.transfertracker.view.team.TeamProfile
import com.transfers.transfertracker.view.team.TeamsList
import com.transfers.transfertracker.view.transfer.TransferList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun AuthNavGraph(navController: NavHostController = rememberNavController(), viewModel: AuthViewModel) {
    NavHost(navController = navController, startDestination = EScreen.SIGN_IN) {
        composable(EScreen.SIGN_IN) { SignIn(viewModel, navController) }
        composable(EScreen.SIGN_UP) {
            SignUp(viewModel) { navController.navigateUp() }
        }
        composable(EScreen.FORGOT_PASSWORD) {
            ForgotPassword(viewModel) { navController.navigateUp() }
        }
    }
}

@Composable
/*
TODO:
  FIX NAV ARGUMENTS
 */
fun DashboardNavGraph(navController: NavHostController = rememberNavController(),
                      navigator: Navigator, signOut: () -> Unit) {
    LaunchedEffect("navigation") {
        navigator.sharedFlow.onEach {
            if(EScreen.DASHBOARD == it){
                navController.navigate(it) {
                    popUpTo(EScreen.DASHBOARD){
                        inclusive = true
                    }
                }
            }else{
                navController.navigate(it)
            }
        }.launchIn(this)
    }

    NavHost(navController = navController, startDestination = EScreen.DASHBOARD) {

        composable(EScreen.DASHBOARD) {

            Dashboard()

        }

        composable(EScreen.SQUAD_LIST) {
            SquadList{ navController.navigateUp() }
        }

        composable("${EScreen.TEAM_LIST}/{team}") {
            TeamsList(it.arguments?.getString("team")) { navController.navigate(EScreen.DASHBOARD) }
        }

        composable(EScreen.COUNTRY_LIST) {

            CountryList { navController.navigate(EScreen.DASHBOARD) }

        }

        composable("${EScreen.LEAGUE_LIST}/{country}") {

            LeaguesList(it.arguments?.getString("country")) { navController.navigate(EScreen.DASHBOARD) }

        }

        composable(EScreen.NEWS_LIST) {

            NewsListScreen{navController.navigateUp()}

        }

        composable("${EScreen.NEWS_WEB_VIEW}/{link}") {
            NewsScreen(link = it.arguments?.getString("link")) { navController.navigateUp() }

        }
//
//        composable("${Screen.PLAYER_PROFILE}/{player}/{team}") {
//
//            val player = it.arguments?.getString("player")
//            val team = it.arguments?.getString("team")
//
//            PlayerProfile(player, team, null) { navController.navigateUp() }
//
//        }

        composable("${EScreen.PLAYER_PROFILE}/{player}/{team}/{league}") {

            val player = it.arguments?.getString("player")
            val team = it.arguments?.getString("team")
            val league = it.arguments?.getString("league")

            Log.println(Log.ASSERT, "Navigation", "PlayerProfile")
            PlayerProfile(player, team, league) { navController.navigateUp() }

        }

        composable(EScreen.TRANSFER_LIST) {

            TransferList{ navController.navigateUp() }

        }

        composable("${EScreen.TEAM_PROFILE}/{team}/{league}") {

            val team = it.arguments?.getString("team")
            val league = it.arguments?.getString("league")

            TeamProfile(team, league) { navController.navigateUp() }

        }

        composable(EScreen.SIGN_OUT) {
            signOut.invoke()
        }
    }
}
