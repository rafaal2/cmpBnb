package org.example.bnb.core.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

/**
 * Implementação 'actual' para plataformas não-Android (como iOS).
 * Criamos uma classe base simples que gerencia seu próprio CoroutineScope.
 */
actual open class ViewModel {
    actual val viewModelScope: CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    // Função para ser chamada no iOS quando a tela for descartada, para limpar o escopo.
    fun onClear() {
        viewModelScope.cancel()
    }
}