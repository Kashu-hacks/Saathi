package com.saathi.app.data.model

/**
 * A single "Family Happiness Guide" suggestion card. MVP uses dummy data
 * (see FamilyActivityRepository); later this can be fetched from Firestore
 * or generated per-day via a Cloud Function.
 */
data class FamilyActivity(
    val title: String,
    val description: String,
    val emoji: String
)
