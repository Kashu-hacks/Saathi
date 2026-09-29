package com.saathi.app.ui.home.dashboards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.saathi.app.ui.components.SectionTitle
import com.saathi.app.ui.components.TipCard
import com.saathi.app.ui.components.WelcomeHeader

private val supportTips = listOf(
    "Ask her how she's really feeling today, and just listen.",
    "Take over a household chore without being asked.",
    "Offer to handle a night feeding so she can sleep.",
    "Compliment her on how well she's adjusting to motherhood."
)

private val suggestedActivities = listOf(
    "Plan a quiet 30-minute walk together this evening.",
    "Cook her favorite meal tonight.",
    "Give her an uninterrupted hour to rest or shower."
)

private val checklist = listOf(
    "Checked in on her emotional well-being",
    "Helped with baby care today",
    "Gave her time to rest",
    "Expressed appreciation for her effort"
)

@Composable
fun HusbandDashboard(name: String, onNavigateToFamilyGuide: () -> Unit) {
    val tip = remember { supportTips.random() }
    val activity = remember { suggestedActivities.random() }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        WelcomeHeader(greeting = "Hi $name \uD83D\uDC4B", subtitle = "Here's how you can support her today.")

        TipCard(title = "Daily Support Tip", description = tip, emoji = "\uD83D\uDC99")
        TipCard(title = "Suggested Activity", description = activity, emoji = "\uD83C\uDF3F")

        SectionTitle("Family Support Checklist")
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                checklist.forEach { item ->
                    Row(modifier = Modifier.padding(vertical = 4.dp)) {
                        Text("\u2022 $item", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }

        OutlinedButton(onClick = onNavigateToFamilyGuide) {
            Text("View Family Happiness Guide")
        }
    }
}
