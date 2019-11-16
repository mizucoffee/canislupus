package net.mizucoffee.canislupus.werewolf

import net.mizucoffee.canislupus.enumerate.PositionEnum

class Thief: Villager() {
    // 入れ替え相手を保持
    override val position: PositionEnum = PositionEnum.THIEF

    override fun getMiniMessage(positions: List<Position>): String? = null
    override fun ability(positions: List<Position>): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}