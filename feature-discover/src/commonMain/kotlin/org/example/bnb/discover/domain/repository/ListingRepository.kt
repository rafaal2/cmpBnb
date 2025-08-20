package org.example.bnb.discover.domain.repository

import org.example.bnb.discover.domain.model.Listing

interface ListingRepository {
    suspend fun getListings() : Result<List<Listing>>
}