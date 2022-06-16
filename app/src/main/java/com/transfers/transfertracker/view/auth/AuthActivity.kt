package com.transfers.transfertracker.view.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.transfers.transfertracker.di.TransferApplication
import com.transfers.transfertracker.di.factory.ViewModelFactory
import com.transfers.transfertracker.view.auth.viewmodel.AuthViewModel
import com.transfers.transfertracker.view.theme.TransferTrackerTheme
import javax.inject.Inject

class AuthActivity : ComponentActivity() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val authViewModel by viewModels<AuthViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as TransferApplication)
            .component
            .authComponent()
            .create()
            .inject(this)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                authViewModel.isUserLoggedIn()
            }
        }

//        setContent {
//            TransferTrackerTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
//                    Greeting()
//                }
//            }
//        }
    }
}

@Composable
fun Greeting() {
    Text(text = "Login Screen")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TransferTrackerTheme {
        Greeting()
    }
}