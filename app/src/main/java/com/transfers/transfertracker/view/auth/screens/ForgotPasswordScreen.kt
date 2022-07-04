package com.transfers.transfertracker.view.auth.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.transfers.transfertracker.model.errors.ErrorTransformer
import com.transfers.transfertracker.repo.impl.AuthRepositoryImpl
import com.transfers.transfertracker.source.impl.AuthSourceImpl
import com.transfers.transfertracker.source.impl.UserSourceImpl
import com.transfers.transfertracker.view.auth.viewmodel.AuthViewModel
import com.transfers.transfertracker.view.theme.TransferTrackerTheme

@Preview(showBackground = true)
@Composable
fun ForgotPasswordPreview() {
    ForgotPasswordScreen()
}

@Composable
fun ForgotPasswordScreen(viewModel: AuthViewModel) = TransferTrackerTheme {

    var emailField by remember { mutableStateOf("") }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (titleText, promptText, emailEditText, sendButton) = createRefs()

        Text(text = "Sign In",
            fontSize = 24.sp,
            modifier = Modifier.constrainAs(titleText) {
                top.linkTo(parent.top, margin = 150.dp)
                start.linkTo(emailEditText.start)
            }
        )

        Text(text = "Please sign in to continue",
            modifier = Modifier.constrainAs(promptText) {
                top.linkTo(titleText.bottom, margin = 5.dp)
                start.linkTo(titleText.start)
            }
        )

        OutlinedTextField(
            value = emailField,
            label = { Text("Email") },
            onValueChange = {emailField = it},
            shape = RoundedCornerShape(50.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.constrainAs(emailEditText) {
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                top.linkTo(promptText.bottom, 175.dp)
            }
        )

        Button(
            shape = RoundedCornerShape(50.dp),
            enabled = viewModel.isEmailValid(emailField),
            onClick = { viewModel.sendResetPasswordEmail(emailField = emailField)},
            modifier = Modifier.constrainAs(sendButton) {
                top.linkTo(emailEditText.bottom, 50.dp)
                end.linkTo(emailEditText.end)
            }) {
            Text(text = "Send")
        }
    }
}

@Composable
fun ForgotPasswordScreen() = TransferTrackerTheme {

    var emailField by remember { mutableStateOf("") }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (titleText, promptText, emailEditText, sendButton) = createRefs()

        Text(text = "Forgot Password",
            fontSize = 24.sp,
            modifier = Modifier.constrainAs(titleText) {
                top.linkTo(parent.top, margin = 150.dp)
                start.linkTo(emailEditText.start)
            }
        )

        Text(text = "Please enter your email to reset password",
            modifier = Modifier.constrainAs(promptText) {
                top.linkTo(titleText.bottom, margin = 5.dp)
                start.linkTo(titleText.start)
            }
        )

        OutlinedTextField(
            value = emailField,
            onValueChange = {emailField = it},
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.constrainAs(emailEditText) {
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                top.linkTo(promptText.bottom, 175.dp)
            }
        )

        Button(
            onClick = { },
            modifier = Modifier.constrainAs(sendButton) {
                end.linkTo(emailEditText.end)
                top.linkTo(emailEditText.bottom, 25.dp)
            }
        ) {
            Text(text = "Send")
        }
    }
}