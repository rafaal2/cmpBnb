package org.example.bnb

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform