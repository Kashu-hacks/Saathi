package com.saathi.app.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.saathi.app.ui.chat.AiChatScreen
import com.saathi.app.ui.familyguide.FamilyGuideScreen
import com.saathi.app.ui.home.HomeScreen
import com.saathi.app.ui.mood.MoodTrackerScreen
import com.saathi.app.ui.navigation.Screen
import com.saathi.app.ui.profile.ProfileScreen

private data class BottomNavItem(val route: String, val label: String, val icon: ImageVector)

/**
 * The post-login shell: bottom navigation + a nested NavHost.
 * Family Guide is reachable via quick-access buttons on the dashboards
 * (it is intentionally not a bottom-nav tab, per the spec).
 */
@Composable
fun MainScaffold(onLoggedOut: () -> Unit) {
    val bottomNavController = rememberNavController()
    val items = listOf(
        BottomNavItem(Screen.Home.route, "Home", Icons.Filled.Home),
        BottomNavItem(Screen.Mood.route, "Mood", Icons.Filled.Mood),
        BottomNavItem(Screen.AiChat.route, "AI Chat", Icons.Filled.Chat),
        BottomNavItem(Screen.Profile.route, "Profile", Icons.Filled.Person)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            bottomNavController.navigate(item.route) {
                                popUpTo(bottomNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onNavigateToFamilyGuide = { bottomNavController.navigate(Screen.FamilyGuide.route) }
                )
            }
            composable(Screen.Mood.route) { MoodTrackerScreen() }
            composable(Screen.AiChat.route) { AiChatScreen() }
            composable(Screen.Profile.route) {
                ProfileScreen(onLogout = onLoggedOut)
            }
            composable(Screen.FamilyGuide.route) { FamilyGuideScreen() }
        }
    }
}
