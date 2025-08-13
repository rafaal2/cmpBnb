package org.example.bnb.game.data.mapper // ou org.example.bnb.game.data.mapper

import org.example.bnb.game.data.remote.response.GameDto // Importe o DTO correto
import org.example.bnb.game.domain.model.Game

// 👇 A função agora opera em UM GameDto de cada vez
fun GameDto.toDomainModel(): Game {
    return Game(
        id = this.id.toString(), // Converta o ID para String se o modelo de domínio espera String
        name = this.name,
        imageUrl = this.backgroundImage ?: "", // Use "" como padrão se a imagem for nula
    )
}