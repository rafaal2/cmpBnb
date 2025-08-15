package org.example.bnb

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hellow, ${platform.name}!"
    }
}