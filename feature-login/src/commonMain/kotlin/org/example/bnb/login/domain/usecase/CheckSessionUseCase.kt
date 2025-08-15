package org.example.bnb.login.domain.usecase

import com.russhwolf.settings.Settings

class CheckSessionUseCase(private val settings: Settings) {
    operator fun invoke(): Boolean {
        return settings.getStringOrNull("user_id") != null
    }
}