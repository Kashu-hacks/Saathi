package com.saathi.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saathi.app.data.model.User
import com.saathi.app.data.repository.AuthRepository
import com.saathi.app.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val authRepository: AuthRepository = AuthRepository(),
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    init {
        loadUser()
    }

    private fun loadUser() {
        val uid = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            userRepository.getUserProfile(uid).onSuccess { _user.value = it }
        }
    }
}
