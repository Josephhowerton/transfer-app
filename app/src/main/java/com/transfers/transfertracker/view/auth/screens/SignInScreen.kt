package com.transfers.transfertracker.view.auth.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.transfers.transfertracker.R
import com.transfers.transfertracker.enums.EScreen
import com.transfers.transfertracker.util.state.AuthFormat
import com.transfers.transfertracker.view.auth.viewmodel.AuthViewModel
import com.transfers.transfertracker.view.theme.HyperLinkBlue
import com.transfers.transfertracker.view.theme.TransferTrackerTheme

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen()
}

@Composable
fun SignInScreen(viewModel: AuthViewModel, navController: NavController) = TransferTrackerTheme {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("")}
    var showPassword by remember { mutableStateOf(false) }
    var transformation: VisualTransformation by remember { mutableStateOf(PasswordVisualTransformation()) }
    var icon by remember { mutableStateOf(R.drawable.ic_baseline_visibility_off_24) }

    val trailingIconView = @Composable {
        IconButton(onClick = {
            showPassword = !showPassword
            if(showPassword){
                transformation = VisualTransformation.None
                icon = R.drawable.ic_baseline_visibility_24
            }else{
                transformation = PasswordVisualTransformation()
                icon = R.drawable.ic_baseline_visibility_off_24
            }
        }){
            Icon(rememberAsyncImagePainter(model = icon),
                contentDescription = "Password Visibility",
                tint = Color.Black)
        }
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (titleText,
            promptText,
            emailEditText,
            passwordEditText,
            forgotPasswordText,
            signInButton,
            signUpText) = createRefs()

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
            value = email,
            onValueChange = {email = AuthFormat.removeWhitespace(it)},
            label = { Text("Email") },
            shape = RoundedCornerShape(50.dp),
            maxLines= 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.padding(bottom = 5.dp, top = 5.dp).constrainAs(emailEditText) {
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                top.linkTo(promptText.bottom, 125.dp)
            }
        )

        OutlinedTextField(
            value = password,
            onValueChange = {password = AuthFormat.removeWhitespace(it)},
            label = { Text("Password") },
            shape = RoundedCornerShape(50.dp),
            visualTransformation = transformation,
            trailingIcon = trailingIconView,
            maxLines= 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.padding(bottom = 5.dp, top = 5.dp).constrainAs(passwordEditText) {
                top.linkTo(emailEditText.bottom, 15.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        ClickableText(
            text = AnnotatedString("Forgot Password"),
            style = TextStyle(color = HyperLinkBlue, fontSize = 18.sp),
            onClick = { navController.navigate(EScreen.FORGOT_PASSWORD) },
            modifier = Modifier.constrainAs(forgotPasswordText) {
                top.linkTo(passwordEditText.bottom, 7.dp)
                end.linkTo(passwordEditText.end)
            }
        )

        Button(
            shape = RoundedCornerShape(50.dp),
            onClick = { viewModel.signIn(email, password) },
            enabled = viewModel.validateSignIn(email, password),
            modifier = Modifier.constrainAs(signInButton) {
                top.linkTo(forgotPasswordText.bottom, 50.dp)
                end.linkTo(forgotPasswordText.end)
            }
        ) {
            Text(text = "Sign In")
        }

        ClickableText(
            text = AnnotatedString("Don't have an account? Start here."),
            onClick = { navController.navigate(EScreen.SIGN_UP) },
            style = TextStyle(color = HyperLinkBlue, fontSize = 18.sp),
            modifier = Modifier
                .constrainAs(signUpText) {
                    bottom.linkTo(parent.bottom, margin = 25.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}

@Composable
fun SignInScreen() = TransferTrackerTheme {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("")}

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (titleText,
            promptText,
            emailEditText,
            passwordEditText,
            forgotPasswordText,
            signInButton,
            signUpText) = createRefs()

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
            value = email,
            onValueChange = {email = it},
            label = { Text("Email") },
            shape = RoundedCornerShape(25.dp),
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.constrainAs(emailEditText) {
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                top.linkTo(promptText.bottom, 175.dp)
            }
        )

        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = { Text("Password") },
            shape = RoundedCornerShape(25.dp),
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.constrainAs(passwordEditText) {
                top.linkTo(emailEditText.bottom, 15.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(text = "Forgot Password?",
            color = Color.Blue,
            modifier = Modifier.constrainAs(forgotPasswordText) {
                top.linkTo(passwordEditText.bottom, 7.dp)
                end.linkTo(passwordEditText.end)
            }
        )

        Button(onClick = { },
            shape = RoundedCornerShape(25.dp),
            modifier = Modifier.constrainAs(signInButton) {
                top.linkTo(forgotPasswordText.bottom, 50.dp)
                end.linkTo(forgotPasswordText.end)
            }
        ) {
            Text(text = "Sign In")
        }

        Text(text = "Don't have an account? Start here.",
            modifier = Modifier
                .constrainAs(signUpText) {
                bottom.linkTo(parent.bottom, margin = 25.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}