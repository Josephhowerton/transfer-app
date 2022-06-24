package com.transfers.transfertracker.view.auth.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.transfers.transfertracker.enums.Screen
import com.transfers.transfertracker.model.errors.ErrorTransformer
import com.transfers.transfertracker.repo.impl.AuthRepositoryImpl
import com.transfers.transfertracker.source.impl.AuthSourceImpl
import com.transfers.transfertracker.source.impl.UserSourceImpl
import com.transfers.transfertracker.view.auth.viewmodel.AuthViewModel
import com.transfers.transfertracker.view.theme.TransferTrackerTheme

//@Preview(showBackground = true)
//@Composable
//fun SignInScreenPreview() {
//    val viewModel = AuthViewModel(AuthRepositoryImpl(UserSourceImpl(), AuthSourceImpl(ErrorTransformer())))
//    TransferTrackerTheme { SignInScreen() }
//}

@Composable
fun SignInScreen(viewModel: AuthViewModel, navController: NavController) = TransferTrackerTheme {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("")}

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)) {
        
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Button(onClick = { viewModel.signIn(email, password) }, enabled = viewModel.validateSignIn(email, password)) {
            Text(text = "Sign In")
        }

        Button(onClick = { navController.navigate(Screen.SIGN_UP.name) }) {
            Text(text = "Sign Up")
        }

        Button(onClick = { navController.navigate(Screen.FORGOT_PASSWORD.name) }) {
            Text(text = "Forgot Password")
        }
    }
}