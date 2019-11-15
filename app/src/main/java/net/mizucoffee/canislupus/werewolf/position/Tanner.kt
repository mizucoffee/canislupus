package net.mizucoffee.canislupus.werewolf.position

import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.werewolf.Camp
import net.mizucoffee.canislupus.werewolf.Camp.*
import net.mizucoffee.canislupus.werewolf.Position
import net.mizucoffee.canislupus.werewolf.hasCamp

class Tanner(override val player: Player? = null) : Position() {
    override val camp: Camp = TANNER
    override var vote: String? = null

    override fun checkWinLose(executed: List<Position>, positions: List<Position>): Int {
        return if (executed.hasCamp(TANNER)) 3 else 0
    }

    override fun ability(positions: List<Position>): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}