package com.app.composeapptemplate.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.app.composeapptemplate.R
import com.app.composeapptemplate.navigation.TopLevelDestination
import com.app.composeapptemplate.ui.components.Loader
import com.app.composeapptemplate.ui.components.VerticalSpacer
import com.app.composeapptemplate.utils.extension.showToast
import timber.log.Timber

@Composable
fun LoginScreen(
    modifier: Modifier,
    loginVM: LoginVM,
    loginScreenUiState: LoginScreenUiState,
    onNavigateClick: (source: String) -> Unit
) {
    val email by remember { loginVM.email }
    val password by remember { loginVM.password }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(loginScreenUiState) {
        when (loginScreenUiState) {
            is LoginScreenUiState.Error -> onNavigateClick(TopLevelDestination.Home.title)
            //showToast(loginScreenUiState.msg)
            is LoginScreenUiState.Initial -> Unit
            is LoginScreenUiState.Success -> {
                onNavigateClick(TopLevelDestination.Home.title)
            }
            else -> Unit
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.bodyLarge,
            )
            VerticalSpacer(15)
            TextField(
                value = email,
                onValueChange = { loginVM.onEmailChange(it) },
                label = { Text(text = stringResource(R.string.enter_email)) }
            )
            VerticalSpacer(15)
            TextField(
                value = password,
                onValueChange = { loginVM.onPasswordChange(it) },
                label = { Text(text = stringResource(R.string.enter_password)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible)
                        R.drawable.eye
                    else R.drawable.hidden

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(image),
                            "",
                            modifier = Modifier.size(
                                22.dp
                            )
                        )
                    }
                }
            )
            VerticalSpacer(15)
            Button(onClick = {
                Timber.e("callho")
                loginVM.login()
            }) {
                Text(text = stringResource(id = R.string.login))
            }
            if (loginScreenUiState is LoginScreenUiState.Loading) {
                Loader(modifier = Modifier.fillMaxSize())
            }
        }

    }
}
