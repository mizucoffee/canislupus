package net.mizucoffee.canislupus.werewolf.position

import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.werewolf.Camp
import net.mizucoffee.canislupus.werewolf.Camp.*
import net.mizucoffee.canislupus.werewolf.Position
import net.mizucoffee.canislupus.werewolf.hasCamp

class Madman(override val player: Player? = null) : Position() {
    override val camp: Camp = MADMAN
    override var vote: String? = null

    override fun checkWinLose(executed: List<Position>, positions: List<Position>): Int {
        return when {
            executed.hasCamp(TANNER) -> 0
            positions.hasCamp(WEREWOLF) && executed.hasCamp(WEREWOLF) -> 0
            positions.hasCamp(WEREWOLF) && executed.hasCamp(MADMAN) -> 2
            positions.hasCamp(WEREWOLF) -> 1
            positions.hasCamp(TANNER) && executed.isEmpty() -> 2
            positions.hasCamp(TANNER) -> 1
            executed.isEmpty() -> 2
            else -> 0
        }
    }

    override fun ability(positions: List<Position>): String? {
        return null
    }
}