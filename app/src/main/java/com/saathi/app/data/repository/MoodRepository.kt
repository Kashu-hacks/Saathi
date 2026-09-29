package com.saathi.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.saathi.app.data.model.MoodEntry
import kotlinx.coroutines.tasks.await

/**
 * Firestore structure:
 *   users/{uid}/moodEntries/{entryId} -> { id, userId, mood, note, timestamp }
 */
class MoodRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private fun moodCollection(userId: String) =
        firestore.collection("users").document(userId).collection("moodEntries")

    suspend fun addMoodEntry(entry: MoodEntry): Result<Unit> {
        return try {
            val docRef = moodCollection(entry.userId).document()
            docRef.set(entry.copy(id = docRef.id)).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMoodHistory(userId: String): Result<List<MoodEntry>> {
        return try {
            val snapshot = moodCollection(userId)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get().await()
            Result.success(snapshot.toObjects(MoodEntry::class.java))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
