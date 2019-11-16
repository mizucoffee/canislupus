package net.mizucoffee.canislupus.werewolf.position

import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.werewolf.enumerate.Camp
import net.mizucoffee.canislupus.werewolf.enumerate.Camp.*
import net.mizucoffee.canislupus.werewolf.Position
import net.mizucoffee.canislupus.werewolf.hasCamp

open class Werewolf : Position() {
    override val camp: Camp = WEREWOLF
    override var vote: String? = null
    override val player: Player? = null

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