package com.saathi.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.saathi.app.data.model.User
import kotlinx.coroutines.tasks.await

/**
 * Handles reads/writes to the Firestore "users" collection.
 * Firestore structure:
 *   users/{uid} -> { uid, name, email, role, createdAt }
 */
class UserRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val usersCollection = firestore.collection("users")

    suspend fun createUserProfile(user: User): Result<Unit> {
        return try {
            usersCollection.document(user.uid).set(user).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserProfile(uid: String): Result<User?> {
        return try {
            val snapshot = usersCollection.document(uid).get().await()
            Result.success(snapshot.toObject(User::class.java))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUserRole(uid: String, role: String): Result<Unit> {
        return try {
            usersCollection.document(uid).update("role", role).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
