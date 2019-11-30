package net.mizucoffee.canislupus.model.viewer

data class Phase5(
    val winner: String,
    val sub: String,
    val voteList: List<String>,
    val cardList: List<String>,
    val abilityList: List<String>
)