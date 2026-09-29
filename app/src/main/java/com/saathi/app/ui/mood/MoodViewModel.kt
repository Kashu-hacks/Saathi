package com.saathi.app.ui.mood

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saathi.app.data.model.MoodEntry
import com.saathi.app.data.repository.AuthRepository
import com.saathi.app.data.repository.MoodRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoodViewModel(
    private val authRepository: AuthRepository = AuthRepository(),
    private val moodRepository: MoodRepository = MoodRepository()
) : ViewModel() {

    private val _history = MutableStateFlow<List<MoodEntry>>(emptyList())
    val history: StateFlow<List<MoodEntry>> = _history

    private val _saveConfirmation = MutableStateFlow(false)
    val saveConfirmation: StateFlow<Boolean> = _saveConfirmation

    init {
        loadHistory()
    }

    fun selectMood(mood: String) {
        val uid = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            val entry = MoodEntry(userId = uid, mood = mood)
            moodRepository.addMoodEntry(entry).onSuccess {
                _saveConfirmation.value = true
                loadHistory()
            }
        }
    }

    fun dismissConfirmation() {
        _saveConfirmation.value = false
    }

    private fun loadHistory() {
        val uid = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            moodRepository.getMoodHistory(uid).onSuccess { _history.value = it }
        }
    }
}
