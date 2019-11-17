package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.ViewModel
import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.werewolf.Position

class GameViewModel : ViewModel() {
    private lateinit var players: ArrayList<Player>
    private lateinit var positionList: ArrayList<Position>
    private lateinit var truePositionList: MutableList<Position>
    private lateinit var punishList: MutableList<Position>
    private var confirmCount: Int = 0

    fun setPlayers(players: ArrayList<Player>) {
        this.players = players
    }

    fun getPlayers() = players

    fun setPositionList(positionList: ArrayList<Position>) {
        this.positionList = positionList
    }

    fun getPositionList() = positionList

    fun setTruePositionList(truePositionList: MutableList<Position>) {
        this.truePositionList = truePositionList
    }

    fun getTruePositionList() = truePositionList

    fun setPunishList(punishList: MutableList<Position>) {
        this.punishList = punishList
    }

    fun getPunishList() = punishList

    fun setConfirmCount(count: Int) {
        this.confirmCount = count
    }

    fun getConfirmCount() = confirmCount

    fun vote(playerId: String, targetId: String) {
        positionList.forEachIndexed { i: Int, it: Position ->
            if (it.player?.id == playerId) positionList[i].vote = targetId
        }
    }

    fun findPositionById(id: String?): Position? {
        for (p in getPositionList())
            if (p.player?.id == id)
                return p
        return null
    }
}