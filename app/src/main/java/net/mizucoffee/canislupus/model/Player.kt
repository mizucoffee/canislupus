package net.mizucoffee.canislupus.model

import java.io.Serializable

data class Player(
    val _id: String,
    val id: String,
    val name: String,
    val win: Int,
    val lose: Int,
    val draw: Int
): Serializable