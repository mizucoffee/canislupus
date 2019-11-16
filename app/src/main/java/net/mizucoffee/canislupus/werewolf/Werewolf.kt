package net.mizucoffee.canislupus.werewolf

import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.enumerate.Camp
import net.mizucoffee.canislupus.enumerate.Camp.*
import net.mizucoffee.canislupus.werewolf.Position
import net.mizucoffee.canislupus.enumerate.PositionEnum
import net.mizucoffee.canislupus.werewolf.hasCamp

open class Werewolf : Position() {
    override val camp: Camp = WEREWOLF
    override var vote: String? = null
    override var player: Player? = null
    override val position: PositionEnum = PositionEnum.WEREWOLF

    override fun getMiniMessage(positions: List<Position>): String? {
        val wolf = positions.filter { it.position == PositionEnum.WEREWOLF }
            .filter { it.player != null }
            .filter { it != this }
        for (position in wolf) {
            println(position)
        }
        return if (wolf.isNotEmpty())
            wolf.map { it.player?.name }.joinToString { "さんと" } + "さんも人狼です"
        else
            "人狼はあなただけです"
    }

    override fun checkWinLose(executed: List<Position>, positions: List<Position>): Int {
        return when {
            executed.hasCamp(TANNER) -> 0
            executed.hasCamp(WEREWOLF) -> 0
            else -> 1
        }
    }

    override fun ability(positions: List<Position>): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}