package com.saathi.app.data.model

/**
 * The four supported roles in Saathi.
 * Stored on the User document in Firestore as the enum's [name].
 */
enum class UserRole(val displayName: String) {
    MOTHER("Mother"),
    HUSBAND("Husband"),
    MOTHER_IN_LAW("Mother-in-law"),
    FAMILY_MEMBER("Family Member");

    companion object {
        fun fromString(value: String?): UserRole? = values().find { it.name == value }
    }
}
