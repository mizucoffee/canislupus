package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.ViewModel
import net.mizucoffee.canislupus.model.Player

class GameViewModel : ViewModel() {
    private lateinit var players: ArrayList<Player>

    fun setPlayers(players: ArrayList<Player>) {
        this.players = players
    }

    fun getPlayers() = players
}