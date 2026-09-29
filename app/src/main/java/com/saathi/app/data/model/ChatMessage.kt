package com.saathi.app.data.model

import java.util.UUID

/**
 * A single message in the Saathi AI chat.
 * Kept in-memory for the MVP; can be persisted to Firestore later
 * under users/{uid}/chatMessages/{id} without changing the UI layer.
 */
data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val isFromUser: Boolean = true,
    val message: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
