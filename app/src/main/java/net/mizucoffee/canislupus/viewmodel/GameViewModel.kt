package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.ViewModel
import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.werewolf.Position

class GameViewModel : ViewModel() {
    private lateinit var players: ArrayList<Player>
    private lateinit var positionList: MutableList<Position>
    private lateinit var executeList: MutableList<Position>
    private var confirmCount: Int = 0

    fun setPlayers(players: ArrayList<Player>) {
        this.players = players
    }

    fun getPlayers() = players

    fun setPositionList(positionList: MutableList<Position>) {
        this.positionList = positionList
    }

    fun getPositionList() = positionList

    fun setExecuteList(executeList: MutableList<Position>) {
        this.executeList = executeList
    }

    fun getExecuteList() = executeList

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
    fun findTruePositionById(id: String?): Position? {
        for (p in getPositionList())
            if (p.truePlayer?.id == id)
                return p
        return null
    }
}