package com.saathi.app.data.model

/**
 * Mirrors a Firestore document at: users/{uid}/moodEntries/{entryId}
 */
data class MoodEntry(
    val id: String = "",
    val userId: String = "",
    val mood: String = "", // stores MoodType.name
    val note: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

enum class MoodType(val label: String, val emoji: String) {
    HAPPY("Happy", "\uD83D\uDE0A"),
    CALM("Calm", "\uD83D\uDE0C"),
    TIRED("Tired", "\uD83D\uDE34"),
    STRESSED("Stressed", "\uD83D\uDE23"),
    SAD("Sad", "\uD83D\uDE22"),
    OVERWHELMED("Overwhelmed", "\uD83E\uDD74")
}
