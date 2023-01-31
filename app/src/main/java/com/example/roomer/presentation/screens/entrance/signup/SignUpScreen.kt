package com.example.roomer.presentation.screens.entrance.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomer.R
import com.example.roomer.presentation.screens.destinations.LoginScreenDestination
import com.example.roomer.presentation.ui_components.*
import com.example.roomer.utils.Consts
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun SignUpScreen1(
    id: Int,
    navigator: DestinationsNavigator,
    signUpScreenViewModel: SignUpScreenViewModel = viewModel()
) {
    var emailValue by rememberSaveable {
        mutableStateOf("")
    }
    var passwordValue by rememberSaveable {
        mutableStateOf("")
    }
    var confirmPasswordValue by rememberSaveable {
        mutableStateOf("")
    }
    var usernameValue by rememberSaveable {
        mutableStateOf("")
    }
    val state = signUpScreenViewModel.state.value
    val focusManager = LocalFocusManager.current

    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) { focusManager.clearFocus() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "Sign Up",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            IconedTextField(
                title = "Username",
                placeholder = "Enter username here",
                onValueChange = {
                    usernameValue = it
                    if (state.isUsernameError)
                        signUpScreenViewModel.clearState()
                },
                value = usernameValue,
                icon = Icons.Filled.VerifiedUser,
                enabled = !state.isLoading,
                isError = state.isUsernameError,
                errorMessage = state.error
            )
            EmailField(
                value = emailValue,
                onValueChange = {
                    emailValue = it
                    if (state.isEmailError)
                        signUpScreenViewModel.clearState()
                },
                enabled = !state.isLoading,
                label = stringResource(id = R.string.email_label),
                placeholder = stringResource(id = R.string.email_placeholder),
                errorMessage = state.error,
                isError = state.isEmailError
            )
            PasswordField(
                value = passwordValue,
                onValueChange = {
                    passwordValue = it
                    if (state.isPasswordError)
                        signUpScreenViewModel.clearState()
                },
                enabled = !state.isLoading,
                label = stringResource(id = R.string.password_label),
                placeholder = stringResource(id = R.string.password_placeholder),
                isError = state.isPasswordError,
                errorMessage = state.error
            )
            PasswordField(
                value = confirmPasswordValue,
                label = stringResource(R.string.conf_pass_label),
                placeholder = stringResource(R.string.conf_pass_placeholder),
                onValueChange = {
                    confirmPasswordValue = it
                    if (state.isConfPasswordError)
                        signUpScreenViewModel.clearState()
                },
                enabled = !state.isLoading,
                isError = state.isConfPasswordError,
                errorMessage = state.error
            )
            GreenButtonPrimary(
                enabled = !state.isLoading,
                text = "Confirm",
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                signUpScreenViewModel.signUpUser(
                    emailValue,
                    passwordValue,
                    usernameValue,
                    confirmPasswordValue
                )
            }
            if (state.success) {
                navigator.navigate(LoginScreenDestination(Consts.signUpScreenId))
            }
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.primary_dark)
                )
            }
            if (state.internetProblem) {
                SimpleAlertDialog(title = stringResource(R.string.login_alert_dialog_title), text = state.error) {
                    signUpScreenViewModel.clearState()
                }
            }
        }
    }
}
