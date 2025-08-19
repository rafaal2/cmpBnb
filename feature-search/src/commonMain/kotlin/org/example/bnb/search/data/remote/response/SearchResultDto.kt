package org.example.bnb.search.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResultDto(
    val id: Int,
    val name: String,
    @SerialName("background_image")
    val backgroundImage: String?,
    val rating: Double,
    val price: Double
)
