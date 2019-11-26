package net.mizucoffee.canislupus.model

data class RPlayer(
    val _id: String,
    val id: String,
    val name: String,
    val win: Int,
    val lose: Int,
    val draw: Int
)