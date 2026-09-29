package com.saathi.app.data.model

/**
 * Mirrors the Firestore document at: users/{uid}
 * No-arg constructor + default values are required for Firestore's
 * automatic object mapping (toObject()).
 */
data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val role: String = "", // empty until role selection is completed
    val createdAt: Long = System.currentTimeMillis()
)
