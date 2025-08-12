package org.example.bnb.core.ui

import androidx.lifecycle.ViewModel as AndroidXViewModel
import androidx.lifecycle.viewModelScope as androidXViewModelScope
import kotlinx.coroutines.CoroutineScope

actual open class ViewModel : AndroidXViewModel() {
    // Mapeamos o viewModelScope para o viewModelScope real do AndroidX.
    actual val viewModelScope: CoroutineScope = androidXViewModelScope
}