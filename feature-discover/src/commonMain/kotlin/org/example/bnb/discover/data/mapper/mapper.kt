package org.example.bnb.discover.data.mapper // ou org.example.bnb.game.data.mapper

import org.example.bnb.discover.data.remote.response.ListingDto
import org.example.bnb.discover.domain.model.Listing

// 👇 A função agora opera em UM GameDto de cada vez
fun ListingDto.toDomainModel(): Listing {
    return Listing(
        id = this.id.toString(), // Converta o ID para String se o modelo de domínio espera String
        name = this.name,
        imageUrl = this.backgroundImage ?: "", // Use "" como padrão se a imagem for nula
        rating = this.rating,
        price = this.price
    )
}