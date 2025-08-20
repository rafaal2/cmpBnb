package org.example.bnb.discover.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class ListingResponse(
    val results: List<ListingDto>
)