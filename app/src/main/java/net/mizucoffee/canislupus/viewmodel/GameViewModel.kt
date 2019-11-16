package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.ViewModel
import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.werewolf.Position

class GameViewModel : ViewModel() {
    private lateinit var players: ArrayList<Player>
    private lateinit var positionList: ArrayList<Position>

    fun setPlayers(players: ArrayList<Player>) {
        this.players = players
    }

    fun getPlayers() = players

    fun setPositionList(positionList: ArrayList<Position>) {
        this.positionList = positionList
    }

    fun getPositionList() = positionList
}