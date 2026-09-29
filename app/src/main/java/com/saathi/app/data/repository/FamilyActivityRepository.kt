package com.saathi.app.data.repository

import com.saathi.app.data.model.FamilyActivity

/**
 * MVP uses static dummy data. A production version could fetch this
 * from a Firestore "familyActivities" collection and rotate daily.
 */
class FamilyActivityRepository {
    fun getDailyActivities(): List<FamilyActivity> = listOf(
        FamilyActivity(
            "Take a short walk together",
            "A gentle walk can boost mood and energy for both of you.",
            "\uD83D\uDEB6"
        ),
        FamilyActivity(
            "Watch a movie together",
            "Relax and enjoy a comforting movie night as a family.",
            "\uD83C\uDFAC"
        ),
        FamilyActivity(
            "Give mother 30 minutes of rest",
            "Take over baby duties so she can rest undisturbed.",
            "\uD83D\uDECF"
        ),
        FamilyActivity(
            "Family gratitude session",
            "Share one thing you're grateful for today, together.",
            "\uD83D\uDE4F"
        ),
        FamilyActivity(
            "Listen to music together",
            "Play her favorite songs and unwind as a family.",
            "\uD83C\uDFB5"
        )
    )
}
