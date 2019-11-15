package net.mizucoffee.canislupus.werewolf.position

import net.mizucoffee.canislupus.werewolf.Camp
import net.mizucoffee.canislupus.werewolf.Position

class Thief: Villager() {
    override val camp: Camp = Camp.VILLAGER
    override var vote: String? = null
    // 入れ替え相手を保持

    override fun ability(positions: List<Position>): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}