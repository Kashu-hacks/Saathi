package com.saathi.app.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saathi.app.data.model.ChatMessage
import com.saathi.app.data.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val chatRepository: ChatRepository = ChatRepository()
) : ViewModel() {

    private val _messages = MutableStateFlow(
        listOf(
            ChatMessage(
                isFromUser = false,
                message = "Hi, I'm Saathi AI. How are you feeling today?"
            )
        )
    )
    val messages: StateFlow<List<ChatMessage>> = _messages

    private val _isTyping = MutableStateFlow(false)
    val isTyping: StateFlow<Boolean> = _isTyping

    fun sendMessage(text: String) {
        if (text.isBlank()) return
        _messages.value = _messages.value + ChatMessage(isFromUser = true, message = text)

        viewModelScope.launch {
            _isTyping.value = true
            val response = chatRepository.getAiResponse(text)
            _isTyping.value = false
            _messages.value = _messages.value + ChatMessage(isFromUser = false, message = response)
        }
    }
}
