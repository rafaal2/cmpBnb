package org.example.bnb.core.ui

import kotlinx.coroutines.CoroutineScope

/**
 * Declaração comum de ViewModel.
 * Toda plataforma (Android, iOS, etc.) deve fornecer uma implementação real ('actual') para esta classe.
 */
expect open class ViewModel() {
    val viewModelScope: CoroutineScope
}