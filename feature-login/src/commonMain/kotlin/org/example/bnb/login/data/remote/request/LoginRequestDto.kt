package org.example.bnb.login.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val login: String,
    val senha: String
)