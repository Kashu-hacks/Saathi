package com.saathi.app.ui.home.dashboards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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

private val affirmations = listOf(
    "You are doing an incredible job, one day at a time.",
    "Your body did something amazing. Be gentle with yourself.",
    "It's okay to ask for help. You don't have to do this alone.",
    "Every small step you take today matters.",
    "You are enough, exactly as you are right now."
)

@Composable
fun MotherDashboard(name: String, onNavigateToFamilyGuide: () -> Unit) {
    val affirmation = remember { affirmations.random() }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        WelcomeHeader(greeting = "Hi $name \uD83C\uDF38", subtitle = "How are you feeling today?")

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Daily Affirmation", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(affirmation, style = MaterialTheme.typography.bodyLarge)
            }
        }

        TipCard(
            title = "Mood Check-in",
            description = "Take a moment to log how you're feeling in the Mood tab.",
            emoji = "\uD83E\uDDE1"
        )

        SectionTitle("Quick Access")
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(onClick = onNavigateToFamilyGuide) {
                Text("Family Guide")
            }
        }
    }
}
