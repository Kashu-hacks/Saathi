package com.saathi.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.saathi.app.data.repository.AuthRepository
import com.saathi.app.ui.auth.LoginScreen
import com.saathi.app.ui.auth.SignupScreen
import com.saathi.app.ui.main.MainScaffold
import com.saathi.app.ui.roleselection.RoleSelectionScreen

/**
 * Top-level (root) navigation graph.
 * Handles the pre-login flow (Login/Signup/RoleSelection) and hands off
 * to [MainScaffold], which owns its own nested NavHost for the bottom-nav
 * destinations (Home / Mood / AI Chat / Profile / Family Guide).
 */
@Composable
fun SaathiNavGraph() {
    val navController = rememberNavController()
    val authRepository = remember { AuthRepository() }
    val startDestination =
        if (authRepository.currentUser != null) Screen.Home.route else Screen.Login.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToSignup = { navController.navigate(Screen.Signup.route) }
            )
        }
        composable(Screen.Signup.route) {
            SignupScreen(
                onSignupSuccess = {
                    navController.navigate(Screen.RoleSelection.route) {
                        popUpTo(Screen.Signup.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }
        composable(Screen.RoleSelection.route) {
            RoleSelectionScreen(
                onRoleSelected = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.RoleSelection.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Home.route) {
            MainScaffold(
                onLoggedOut = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}
