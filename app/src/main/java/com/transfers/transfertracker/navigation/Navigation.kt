package com.transfers.transfertracker.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.transfers.transfertracker.di.TransferApplication
import com.transfers.transfertracker.di.daggerViewModel
import com.transfers.transfertracker.enums.Screen
import com.transfers.transfertracker.view.MainActivity
import com.transfers.transfertracker.view.auth.AuthActivity
import com.transfers.transfertracker.view.auth.ForgotPassword
import com.transfers.transfertracker.view.auth.SignIn
import com.transfers.transfertracker.view.auth.SignUp
import com.transfers.transfertracker.view.auth.viewmodel.AuthViewModel
import com.transfers.transfertracker.view.components.NewsScreen
import com.transfers.transfertracker.view.components.PlayerProfile
import com.transfers.transfertracker.view.country.CountryList
import com.transfers.transfertracker.view.league.*
import com.transfers.transfertracker.view.dashboard.Dashboard
import com.transfers.transfertracker.view.news.NewsListScreen
import com.transfers.transfertracker.view.player.PlayersList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun AuthNavGraph(navController: NavHostController = rememberNavController(), viewModel: AuthViewModel) {
    NavHost(navController = navController, startDestination = Screen.SIGN_IN) {
        composable(Screen.SIGN_IN) { SignIn(viewModel, navController) }
        composable(Screen.SIGN_UP) { SignUp(viewModel) }
        composable(Screen.FORGOT_PASSWORD) { ForgotPassword(viewModel) }
    }
}

@Composable
fun DashboardNavGraph(navController: NavHostController = rememberNavController(),
                      activity: MainActivity,
                      application: TransferApplication,
                      navigator: Navigator,
) {
    LaunchedEffect("navigation") {
        navigator.sharedFlow.onEach {
            navController.navigate(it)
        }.launchIn(this)
    }

    NavHost(navController = navController, startDestination = Screen.DASHBOARD) {

        composable(Screen.DASHBOARD) {
            val viewModel = daggerViewModel{
                application.dashboardComponent.getDashboardViewModel()
            }

            Dashboard(viewModel = viewModel)
        }
        composable(Screen.PLAYER_LIST) {
            val viewModel = daggerViewModel{
                application.playersListComponent.getPlayersListViewModel()
            }

            PlayersList(viewModel = viewModel)
        }
        composable(Screen.TEAM_LIST) {
            val viewModel = daggerViewModel{
                application.teamListComponent.getTeamsListViewModel()
            }

            TeamsList(viewModel = viewModel)
        }
        composable(Screen.COUNTRY_LIST) {

            val viewModel = daggerViewModel {
                application.countryListComponent.getCountListViewModel()
            }

            CountryList(viewModel = viewModel)
        }
        composable(Screen.LEAGUE_LIST) {
            val viewModel = daggerViewModel{
                application.leaguesListComponent.getLeaguesListViewModel()
            }
            val country = it.arguments?.getString("country")
            LeaguesList(country, viewModel = viewModel)
        }
        composable(Screen.NEWS_LIST) {
            val viewModel = daggerViewModel{
                application.newsComponent.getNewsListViewModel()
            }

            NewsListScreen(viewModel)
        }
        composable(Screen.NEWS_WEB_VIEW) {
            val viewModel = daggerViewModel{
                application.dashboardComponent.getDashboardViewModel()
            }

            NewsScreen(viewModel = viewModel)
        }
        composable(Screen.PLAYER_PROFILE) {
            val viewModel = daggerViewModel{
                application.playerProfileComponent.getPlayerProfileViewModel()
            }

            PlayerProfile(viewModel)
        }
        composable(Screen.TEAM_PROFILE) {
            val viewModel = daggerViewModel{
                application.teamProfileComponent.getTeamProfileViewModel()
            }

//            TeamProfile(viewModel = viewModel)
        }

        composable(Screen.SIGN_OUT) {
            activity.finish()
        }
    }
}
