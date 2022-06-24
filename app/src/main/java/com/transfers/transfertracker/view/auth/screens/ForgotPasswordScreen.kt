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
import androidx.navigation.NavController
import com.transfers.transfertracker.view.auth.viewmodel.AuthViewModel
import com.transfers.transfertracker.view.theme.TransferTrackerTheme

//@Preview(showBackground = true)
//@Composable
//fun ForgotPasswordPreview() {
//    val passwordResetViewModel = ForgotPasswordViewModel(AuthRepositoryImpl(UserSourceImpl(), AuthSourceImpl(ErrorTransformer())))
//    TransferTrackerTheme {
//        ForgotPasswordScreen(passwordResetViewModel)
//    }
//}

@Composable
fun ForgotPasswordScreen(viewModel: AuthViewModel) = TransferTrackerTheme {

    var emailField by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background)) {

        OutlinedTextField(
            value = emailField,
            onValueChange = { emailField = it },
            label = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Button(
            onClick = { viewModel.sendResetPasswordEmail(emailField = emailField)},
            enabled = viewModel.isEmailValid(emailField)
        ) {
            Text(text = "Send")
        }
    }
}