package com.faceme.faceme.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.faceme.faceme.R
import com.faceme.faceme.ui.login_sign_up.LoginRegisterUiState
import com.faceme.faceme.ui.login_sign_up.LoginViewModel
import com.faceme.faceme.ui.login_sign_up.RegisterViewModel
import com.faceme.faceme.ui.theme.FaceMeTheme
import com.faceme.faceme.utils.WindowSize
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginScreen(
    windowSize: WindowSize
) {
    FaceMeTheme {
        val systemUiController = rememberSystemUiController()
        val darkIcons = MaterialTheme.colors.isLight
        SideEffect {
            systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = darkIcons)
        }

        val coroutineScope = rememberCoroutineScope()

        val loginViewModel = LoginViewModel()

        val registerViewModel = RegisterViewModel()

        val registrationDrawerState =
            rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

        Column(
            Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .windowInsetsPadding(
                    WindowInsets.navigationBars.only(WindowInsetsSides.Bottom + WindowInsetsSides.Top)
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginForm(
                uiState = loginViewModel.uiState.collectAsState().value as LoginRegisterUiState.IsLogin,
                onUserNameChanged = { loginViewModel.onUserNameChanged(it) },
                onPasswordChanged = { loginViewModel.onPasswordChanged(it) },
                onClickLogin = { loginViewModel.loginUser() }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            // Register button
            TextButton(onClick = { coroutineScope.launch { registrationDrawerState.show() } }) {
                Text(text = stringResource(R.string.register))
            }
        }
        ModalBottomSheetLayout(
            sheetState = registrationDrawerState,
            sheetContent = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = { coroutineScope.launch { registrationDrawerState.hide() } }
                    ) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = stringResource(id = R.string.close_registration_sheet),
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    }
                }
                RegisterForm(
                    uiState = registerViewModel.uiState.collectAsState().value as LoginRegisterUiState.IsRegister,
                    onUserNameChanged = { registerViewModel.onUserNameChanged(it) },
                    onPasswordChanged = { registerViewModel.onPasswordChanged(it) },
                    onFirstNameChanged = { registerViewModel.onFirstNameChanged(it) },
                    onLastNameChanged = { registerViewModel.onLastNameChanged(it) },
                    onDisplayNameChanged = { registerViewModel.onDisplayNameChanged(it) },
                    onClickRegister = { registerViewModel.registerUser() },
                    onClickVerify = { registerViewModel.verify() },
                    onVerificationCodeChanged = { registerViewModel.onVerificationCodeChanged(it) }
                )
            },
            modifier = Modifier.fillMaxSize()
        ) {}
    }
}

@Composable
fun LoginForm(
    uiState: LoginRegisterUiState.IsLogin,
    onUserNameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onClickLogin: () -> Unit,
) {
    var passwordVisible = false
    OutlinedTextField(
        value = uiState.userName,
        onValueChange = onUserNameChanged,
        label = { Text(text = stringResource(R.string.email)) },
        maxLines = 1,
    )
    Spacer(modifier = Modifier.padding(8.dp))
    OutlinedTextField(
        value = uiState.password,
        onValueChange = onPasswordChanged,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            // Please provide localized description for accessibility services
            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, description)
            }
        },
        label = { Text(text = stringResource(R.string.password)) },
        maxLines = 1
    )
    Spacer(modifier = Modifier.padding(8.dp))
    TextButton(onClick = onClickLogin) {
        Text(text = stringResource(R.string.login))
    }
}

@Composable
fun RegisterForm(
    uiState: LoginRegisterUiState.IsRegister,
    onUserNameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onFirstNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onDisplayNameChanged: (String) -> Unit,
    onVerificationCodeChanged: (String) -> Unit,
    onClickRegister: () -> Unit,
    onClickVerify: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var passwordVisible = false
        OutlinedTextField(
            value = uiState.userName,
            onValueChange = onUserNameChanged,
            label = { Text(text = stringResource(R.string.email)) },
            maxLines = 1,
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = uiState.password,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Please provide localized description for accessibility services
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description)
                }
            },
            onValueChange = onPasswordChanged,
            label = { Text(text = stringResource(R.string.password)) },
            maxLines = 1
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = uiState.firstName,
            onValueChange = onFirstNameChanged,
            label = { Text(text = stringResource(R.string.first_name)) },
            maxLines = 1,
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = uiState.lastName,
            onValueChange = onLastNameChanged,
            label = { Text(text = stringResource(R.string.last_name)) },
            maxLines = 1
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = uiState.displayName,
            onValueChange = onDisplayNameChanged,
            label = { Text(text = stringResource(R.string.display_name)) },
            maxLines = 1
        )
        Spacer(modifier = Modifier.padding(8.dp))
        TextButton(
            onClick = onClickRegister
        ) {
            Text(text = stringResource(id = R.string.register))
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Row {
            OutlinedTextField(
                enabled = uiState.showVerification,
                value = uiState.verificationCode,
                onValueChange = onVerificationCodeChanged,
                label = { Text(text = stringResource(id = R.string.verification_code)) },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                )
            )
            Button(enabled = uiState.showVerification, onClick = onClickVerify) {
                Text(
                    text = stringResource(id = R.string.verify),
                )
            }
        }
    }
}

