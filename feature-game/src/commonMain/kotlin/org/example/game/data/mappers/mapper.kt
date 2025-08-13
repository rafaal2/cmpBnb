package org.example.game.data.mappers

import org.example.coreNetwork.model.game.Result
import org.example.game.domain.model.Game

fun List<Result>.toDomainListOfGames() : List<Game> = map {
    Game(
        id = it.id,
        name = it.name,
        imageUrl = it.background_image
    )
}