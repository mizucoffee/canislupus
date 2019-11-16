package net.mizucoffee.canislupus.werewolf.position

import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.werewolf.Position

class Seer : Villager() {
    override fun ability(positions: List<Position>): String? {
        return null
    }
}