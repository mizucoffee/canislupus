package net.mizucoffee.canislupus.model

import java.io.Serializable

data class Game(
    val id: String,
    val phase: Int,
    val data: String
) : Serializable