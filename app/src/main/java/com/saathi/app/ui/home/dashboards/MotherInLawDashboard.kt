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

private val recoveryTips = listOf(
    "Encourage her to rest as much as possible during recovery.",
    "Remind her that healing takes time, physically and emotionally."
)

private val nutritionTips = listOf(
    "Prepare warm, nutrient-rich meals like soups and dals.",
    "Encourage plenty of fluids and iron-rich foods for recovery."
)

private val familyCareAdvice = listOf(
    "Offer to hold the baby so she can nap.",
    "Share your experience gently, without pressure or judgement."
)

@Composable
fun MotherInLawDashboard(name: String, onNavigateToFamilyGuide: () -> Unit) {
    val recoveryTip = remember { recoveryTips.random() }
    val nutritionTip = remember { nutritionTips.random() }
    val careAdvice = remember { familyCareAdvice.random() }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        WelcomeHeader(greeting = "Hi $name \uD83C\uDF37", subtitle = "Ways to support the new mother today.")

        TipCard(title = "Recovery Support", description = recoveryTip, emoji = "\uD83E\uDE77")
        TipCard(title = "Nutrition Suggestion", description = nutritionTip, emoji = "\uD83C\uDF72")
        TipCard(title = "Family Care Advice", description = careAdvice, emoji = "\uD83E\uDD17")

        OutlinedButton(onClick = onNavigateToFamilyGuide) {
            Text("View Family Happiness Guide")
        }
    }
}
