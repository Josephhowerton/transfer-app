package com.transfers.transfertracker.view.navigation

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.transfers.transfertracker.enums.Screen
import com.transfers.transfertracker.view.auth.ForgotPassword
import com.transfers.transfertracker.view.auth.SignIn
import com.transfers.transfertracker.view.auth.SignUp
import com.transfers.transfertracker.view.auth.viewmodel.AuthViewModel

@Composable
fun AuthNavGraph(navController: NavHostController = rememberNavController(), viewModel: AuthViewModel) {
    NavHost(navController = navController, startDestination = Screen.SIGN_IN.name) {
        composable(Screen.SIGN_IN.name) { SignIn(viewModel, navController) }
        composable(Screen.SIGN_UP.name) { SignUp(viewModel) }
        composable(Screen.FORGOT_PASSWORD.name) { ForgotPassword(viewModel) }
    }
}
