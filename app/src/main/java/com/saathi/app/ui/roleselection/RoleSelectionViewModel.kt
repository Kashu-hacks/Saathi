package com.saathi.app.ui.roleselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saathi.app.data.repository.AuthRepository
import com.saathi.app.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RoleSelectionViewModel(
    private val authRepository: AuthRepository = AuthRepository(),
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {

    private val _saved = MutableStateFlow(false)
    val saved: StateFlow<Boolean> = _saved

    fun saveRole(role: String) {
        val uid = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            userRepository.updateUserRole(uid, role).onSuccess {
                _saved.value = true
            }
        }
    }
}
