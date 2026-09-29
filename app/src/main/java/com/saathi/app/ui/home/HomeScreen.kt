package com.saathi.app.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.saathi.app.data.model.UserRole
import com.saathi.app.ui.home.dashboards.FamilyMemberDashboard
import com.saathi.app.ui.home.dashboards.HusbandDashboard
import com.saathi.app.ui.home.dashboards.MotherDashboard
import com.saathi.app.ui.home.dashboards.MotherInLawDashboard

/**
 * Routes to the correct dashboard based on the signed-in user's role.
 * This is the single place that needs to change if new roles are added.
 */
@Composable
fun HomeScreen(
    onNavigateToFamilyGuide: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val user by viewModel.user.collectAsState()
    val currentUser = user

    if (currentUser == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    when (UserRole.fromString(currentUser.role)) {
        UserRole.MOTHER -> MotherDashboard(name = currentUser.name, onNavigateToFamilyGuide = onNavigateToFamilyGuide)
        UserRole.HUSBAND -> HusbandDashboard(name = currentUser.name, onNavigateToFamilyGuide = onNavigateToFamilyGuide)
        UserRole.MOTHER_IN_LAW -> MotherInLawDashboard(name = currentUser.name, onNavigateToFamilyGuide = onNavigateToFamilyGuide)
        UserRole.FAMILY_MEMBER -> FamilyMemberDashboard(name = currentUser.name, onNavigateToFamilyGuide = onNavigateToFamilyGuide)
        null -> FamilyMemberDashboard(name = currentUser.name, onNavigateToFamilyGuide = onNavigateToFamilyGuide)
    }
}
