package com.app.composeapptemplate.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.composeapptemplate.ui.home.HomeScreen
import com.app.composeapptemplate.ui.login.LoginScreen
import com.app.composeapptemplate.ui.login.LoginVM

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = TopLevelDestination.Login.route
    ) {
        composable(route = TopLevelDestination.Login.route) {
            val loginVM: LoginVM = hiltViewModel()
            val loginUiState by remember { loginVM.loginResponse }.collectAsStateWithLifecycle()
            LoginScreen(
                modifier = modifier,
                loginVM = loginVM,
                loginScreenUiState = loginUiState,
                onNavigateClick = { source ->
                    navController.navigate(source)
                }
            )
        }
        composable(route = TopLevelDestination.Home.route) {
            HomeScreen(modifier = modifier)
        }
    }
}