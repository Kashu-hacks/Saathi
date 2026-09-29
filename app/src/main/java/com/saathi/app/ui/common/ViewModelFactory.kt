package com.saathi.app.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Minimal generic ViewModel factory. Keeps this MVP free of a DI framework
 * (Hilt/Koin) while still allowing constructor-injected repositories,
 * which makes ViewModels easy to unit test later.
 *
 * Usage:
 *   val viewModel: MoodViewModel = viewModel(factory = GenericViewModelFactory { MoodViewModel() })
 */
class GenericViewModelFactory<T : ViewModel>(
    private val creator: () -> T
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <U : ViewModel> create(modelClass: Class<U>): U {
        return creator() as U
    }
}
