package com.vinson.baseui.ui.util

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseLoadingComposeViewModel : BaseComposeViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading
    protected open val scope = viewModelScope

    protected fun sendApi(apiAction: suspend CoroutineScope.() -> Unit) {
        scope.launch {
            _isLoading.emit(true)
            apiAction()
            _isLoading.emit(false)
        }
    }
}