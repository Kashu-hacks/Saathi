package com.saathi.app.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")
    object RoleSelection : Screen("role_selection")
    object Home : Screen("home")
    object Mood : Screen("mood")
    object AiChat : Screen("ai_chat")
    object Profile : Screen("profile")
    object FamilyGuide : Screen("family_guide")
}
