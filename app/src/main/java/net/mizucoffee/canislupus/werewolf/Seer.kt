package net.mizucoffee.canislupus.werewolf

import net.mizucoffee.canislupus.enumerate.PositionEnum

class Seer : Villager() {
    override val position: PositionEnum = PositionEnum.SEER

    override fun getMiniMessage(positions: List<Position>): String? = null
    override fun ability(positions: List<Position>): String? {
        return null
    }
}