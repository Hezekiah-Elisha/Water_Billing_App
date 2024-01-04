package com.olefaent.waterbilling.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.olefaent.waterbilling.model.LoginRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController : NavController, modifier: Modifier = Modifier){
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }

    val userViewModel: UserViewModel = viewModel(
        factory = UserViewModel.Factory
    )

    val mState = userViewModel.uiState

    Column(
        modifier = modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome back!",
            style = MaterialTheme.typography.headlineLarge
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            supportingText = { Text("Enter your email") },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Email, contentDescription = "Email")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            supportingText = { Text("Enter your Password") },
            visualTransformation =
            if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Create, contentDescription = "Password")
            },
            trailingIcon = {
                IconButton(onClick = { passwordHidden = !passwordHidden }) {
                    val visibilityIcon =
                        if (passwordHidden) Icons.Filled.Done else Icons.Filled.Close
                    // Please provide localized description for accessibility services
                    val description = if (passwordHidden) "Show password" else "Hide password"
                    Icon(imageVector = visibilityIcon, contentDescription = description)
                }
            },
            singleLine = true,
        )
        Button(
            onClick = {
                userViewModel.setEmail(email)
                userViewModel.setPassword(password)

                Log.d("LOGIN", "LoginScreen: Email: ${userViewModel.getTheEmail()}")
                Log.d("LOGIN", "LoginScreen: password: $password")

                userViewModel.loginUser(
                    email = email,
                    password = password
                )

                Log.d("LOGIN", "LoginScreen: $mState")

                if (mState is UserState.Success){
                    Log.d("LOGIN", "LoginScreen: ${mState.loginResponse}")

                    userViewModel.saveAccessToken(mState.loginResponse.accessToken)
                    userViewModel.setUserLoggedIn(mState.loginResponse.user)
                    val token = userViewModel.getAccessToken()
                    val user = userViewModel.getUserLoggedIn()

                    Log.d("LOGIN", "LoginScreen: Access Token: ${mState.loginResponse.accessToken}")
                    Log.d("LOGIN", "LoginScreen: nAccess Token: $token\n User: $user")

                    if (user != null){
                        navController.navigate("home")
                    }else{
                        Log.d("LOGIN", "LoginScreen: User is null")
                    }

                } else if (mState is UserState.Error){
                    Log.d("LOGIN", "LoginScreen: ${mState.message}")
                }


            }
        ) {
            Text(text = "Login")
        }

    }

}

//@Preview(showBackground = true,
//    showSystemUi = true)
//@Composable
//fun LoginScreenPreview(){
//    LoginScreen()
//}