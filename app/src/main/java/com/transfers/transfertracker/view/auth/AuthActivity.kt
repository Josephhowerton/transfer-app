package com.transfers.transfertracker.view.auth

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import com.transfers.transfertracker.R
import com.transfers.transfertracker.di.TransferApplication
import com.transfers.transfertracker.di.factory.ViewModelFactory
import com.transfers.transfertracker.model.result.AuthResultError
import com.transfers.transfertracker.model.result.AuthResultSuccess
import com.transfers.transfertracker.view.auth.screens.ForgotPasswordScreen
import com.transfers.transfertracker.view.auth.screens.SignInScreen
import com.transfers.transfertracker.view.auth.screens.SignUpScreen
import com.transfers.transfertracker.view.auth.viewmodel.AuthViewModel
import com.transfers.transfertracker.view.main.MainActivity
import com.transfers.transfertracker.view.navigation.AuthNavGraph
import com.transfers.transfertracker.view.theme.TransferTrackerTheme
import javax.inject.Inject

class AuthActivity : ComponentActivity() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by viewModels<AuthViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as TransferApplication)
            .component
            .authComponent()
            .create()
            .inject(this)

        lifecycle.addObserver(viewModel)

        viewModel.result.observe(this) {
            if(it is AuthResultSuccess){
                navigateToMain()
            } else {
                val title = (it as AuthResultError).errorTitle
                val message = it.errorMessage
                displayError(getString(title), getString(message))
            }
        }

        installSplashScreen().apply {
            setKeepOnScreenCondition { viewModel.isUserLoggedIn() }
        }

        setContent {
            TransferTrackerTheme {
                if(viewModel.isUserLoggedIn()){ navigateToMain() }
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    AuthNavGraph(viewModel = viewModel)
                }
            }
        }
    }

    private fun navigateToMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun displayError(title: String, message: String){
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setNegativeButton(R.string.action_try_again)
            { dialogInterface, _ -> dialogInterface.dismiss() }
            .show()
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    val signInViewModel = SignInViewModel(AuthRepositoryImpl(UserSourceImpl(), AuthSourceImpl(ErrorTransformer())))
//
//    TransferTrackerTheme {
//        SignInScreen(signInViewModel)
//    }
//}

@Composable
fun SignIn(viewModel: AuthViewModel, navController: NavHostController){
    SignInScreen(viewModel, navController)
}

@Composable
fun SignUp(viewModel: AuthViewModel){
    SignUpScreen(viewModel)
}

@Composable
fun ForgotPassword(viewModel: AuthViewModel){
    ForgotPasswordScreen(viewModel)
}

