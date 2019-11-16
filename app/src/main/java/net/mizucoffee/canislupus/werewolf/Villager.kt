package net.mizucoffee.canislupus.werewolf

import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.enumerate.Camp
import net.mizucoffee.canislupus.enumerate.Camp.*
import net.mizucoffee.canislupus.enumerate.PositionEnum

open class Villager : Position() {
    override val camp: Camp = VILLAGER
    override var vote: String? = null
    override var player: Player? = null
    override val position: PositionEnum = PositionEnum.VILLAGER

    override fun getMiniMessage(positions: List<Position>): String? = null

    override fun checkWinLose(executed: List<Position>, positions: List<Position>): Int {
        return when {
            executed.hasCamp(TANNER) -> 0
            executed.hasCamp(WEREWOLF) -> 1
            !positions.hasCamp(WEREWOLF) && positions.hasCamp(TANNER) && executed.isEmpty() -> 2
            !positions.hasCamp(WEREWOLF) && positions.hasCamp(TANNER) && executed.isNotEmpty() -> 1
            !positions.hasCamp(WEREWOLF) && !positions.hasCamp(TANNER) && executed.isEmpty() -> 2
            else -> 0
        }
    }

    override fun ability(positions: List<Position>): String? {
        return null
    }
}