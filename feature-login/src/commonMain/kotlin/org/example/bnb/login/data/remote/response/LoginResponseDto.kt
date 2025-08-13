package org.example.bnb.login.data.remote.response

import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) para a resposta de login.
 * Os nomes das variáveis (success, id, error) devem corresponder exatamente às chaves do JSON
 * que seu PHP envia com 'json_encode'.
 * 'id' e 'error' são nuláveis porque um ou outro pode não estar presente na resposta.
 */
@Serializable
data class LoginResponseDto(
    val success: Boolean,
    val id: Int? = null,
    val error: String? = null
)