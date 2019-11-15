package net.mizucoffee.canislupus.werewolf.position

import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.werewolf.Camp
import net.mizucoffee.canislupus.werewolf.Camp.*
import net.mizucoffee.canislupus.werewolf.Position

class Seer(override val player: Player?) : Villager() {
    override val camp: Camp = VILLAGER
    override var vote: String? = null

    override fun ability(positions: List<Position>): String? {
        return null
    }
}