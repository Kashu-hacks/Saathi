package com.saathi.app.ui.home.dashboards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.saathi.app.ui.components.TipCard
import com.saathi.app.ui.components.WelcomeHeader

private val supportSuggestions = listOf(
    "Offer to run an errand so she has one less thing to worry about.",
    "Send a thoughtful message letting her know you're thinking of her."
)

private val encouragementIdeas = listOf(
    "Compliment her on how well she is doing as a new mother.",
    "Remind her that it's okay to not have everything figured out."
)

@Composable
fun FamilyMemberDashboard(name: String, onNavigateToFamilyGuide: () -> Unit) {
    val suggestion = remember { supportSuggestions.random() }
    val idea = remember { encouragementIdeas.random() }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        WelcomeHeader(greeting = "Hi $name \uD83D\uDC90", subtitle = "Small gestures make a big difference.")

        TipCard(title = "Support Suggestion", description = suggestion, emoji = "\uD83C\uDF3C")
        TipCard(title = "Encouragement Idea", description = idea, emoji = "\u2728")

        OutlinedButton(onClick = onNavigateToFamilyGuide) {
            Text("View Family Happiness Guide")
        }
    }
}
