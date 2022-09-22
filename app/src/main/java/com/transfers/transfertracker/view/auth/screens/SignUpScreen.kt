package com.transfers.transfertracker.view.auth.screens

import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.transfers.transfertracker.R
import com.transfers.transfertracker.view.auth.viewmodel.AuthViewModel
import com.transfers.transfertracker.view.component.TransferTopAppBar
import com.transfers.transfertracker.view.theme.TransferTrackerTheme

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen()
}

@Composable
fun SignUpScreen() = TransferTrackerTheme {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var transformation: VisualTransformation by remember { mutableStateOf(
        PasswordVisualTransformation()
    ) }
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
            Icon(
                rememberAsyncImagePainter(model = icon),
                contentDescription = "Password Visibility",
                tint = Color.Black)
        }
    }

    ConstraintLayout(Modifier.fillMaxSize()) {
        val (titleText, promptText, nameEditText, emailEditText, passwordEditText, signUpButton) = createRefs()

        Text(text = "Sign Up",
            fontSize = 24.sp,
            modifier = Modifier.constrainAs(titleText) {
                top.linkTo(parent.top, margin = 75.dp)
                start.linkTo(nameEditText.start)
            }
        )

        Text(text = "Please enter your details to get started", modifier = Modifier.constrainAs(promptText) {
                top.linkTo(titleText.bottom, margin = 5.dp)
                start.linkTo(titleText.start)
            }
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Name") },
            shape = RoundedCornerShape(50.dp),
            maxLines= 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.constrainAs(nameEditText) {
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                top.linkTo(promptText.bottom, 175.dp)
            }
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            shape = RoundedCornerShape(50.dp),
            maxLines= 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.constrainAs(emailEditText) {
                top.linkTo(nameEditText.bottom, 15.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        OutlinedTextField(
            maxLines= 1,
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            shape = RoundedCornerShape(50.dp),
            trailingIcon = trailingIconView,
            visualTransformation = transformation,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.constrainAs(passwordEditText) {
                top.linkTo(emailEditText.bottom, 15.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Button(onClick = { },
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier.constrainAs(signUpButton) {
                top.linkTo(passwordEditText.bottom, 50.dp)
                end.linkTo(passwordEditText.end)
            }) {
            Text(text = "Sign Up")
        }
    }
}

@Composable
fun SignUpScreen(viewModel: AuthViewModel, onErrorAction: () -> Unit) = TransferTrackerTheme {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var transformation: VisualTransformation by remember { mutableStateOf(
        PasswordVisualTransformation()
    ) }
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
            Icon(
                rememberAsyncImagePainter(model = icon),
                contentDescription = "Password Visibility",
                tint = Color.Black)
        }
    }

    Scaffold(topBar = {

        TransferTopAppBar(stringResource(id = R.string.action_sign_up), false, onErrorAction)

    }) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()){
            val (titleText, promptText, nameEditText, emailEditText, passwordEditText, signUpButton) = createRefs()

            Text(text = "Sign Up",
                fontSize = 24.sp,
                modifier = Modifier.constrainAs(titleText) {
                    top.linkTo(parent.top, margin = 75.dp)
                    start.linkTo(nameEditText.start)
                }
            )

            Text(text = "Please enter your details to get started", modifier = Modifier.constrainAs(promptText) {
                top.linkTo(titleText.bottom, margin = 5.dp)
                start.linkTo(titleText.start)
            }
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "Name") },
                shape = RoundedCornerShape(50.dp),
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.constrainAs(nameEditText) {
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    top.linkTo(promptText.bottom, 125.dp)
                }
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") },
                shape = RoundedCornerShape(50.dp),
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.constrainAs(emailEditText) {
                    top.linkTo(nameEditText.bottom, 15.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password") },
                shape = RoundedCornerShape(50.dp),
                singleLine = true,
                maxLines = 1,
                trailingIcon = trailingIconView,
                visualTransformation = transformation,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.constrainAs(passwordEditText) {
                    top.linkTo(emailEditText.bottom, 15.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            Button(
                shape = RoundedCornerShape(50.dp),
                onClick = { viewModel.signUp(name, email, password)},
                enabled = viewModel.validateSignUp(name, email, password),
                modifier = Modifier.constrainAs(signUpButton) {
                    top.linkTo(passwordEditText.bottom, 50.dp)
                    end.linkTo(passwordEditText.end)
                }) {
                Text(text = "Sign Up")
            }
        }
    }
}