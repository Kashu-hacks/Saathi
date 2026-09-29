package com.saathi.app.ui.mood

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.saathi.app.data.model.MoodType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MoodTrackerScreen(viewModel: MoodViewModel = viewModel()) {
    val history by viewModel.history.collectAsState()
    val saveConfirmation by viewModel.saveConfirmation.collectAsState()

    if (saveConfirmation) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissConfirmation() },
            confirmButton = {
                TextButton(onClick = { viewModel.dismissConfirmation() }) { Text("OK") }
            },
            title = { Text("Mood saved") },
            text = { Text("Thank you for checking in with yourself today.") }
        )
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("How are you feeling?", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(MoodType.values().toList()) { mood ->
                Card(onClick = { viewModel.selectMood(mood.name) }) {
                    Column(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                        Text(mood.emoji, style = MaterialTheme.typography.displaySmall)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(mood.label, style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Recent Mood History", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))

        val sdf = remember { SimpleDateFormat("MMM dd, hh:mm a", Locale.getDefault()) }
        history.take(5).forEach { entry ->
            val moodType = MoodType.values().find { it.name == entry.mood }
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Text("${moodType?.emoji ?: ""} ${moodType?.label ?: entry.mood}")
                Spacer(modifier = Modifier.weight(1f))
                Text(sdf.format(Date(entry.timestamp)), style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
