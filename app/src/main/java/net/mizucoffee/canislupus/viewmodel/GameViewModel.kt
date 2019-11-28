package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.model.werewolf.Card

class GameViewModel : ViewModel() {
    private lateinit var players: MutableList<Player>
    private lateinit var cardList: MutableList<Card>
    private lateinit var executeList: MutableList<Card>
    var gameId = MutableLiveData<String>()
    private var confirmCount: Int = 0

    fun getPlayers() = players
    fun setPlayers(players: MutableList<Player>) {
        this.players = players
    }

    fun getCardList() = cardList
    fun setCardList(cardList: MutableList<Card>) {
        this.cardList = cardList
    }

    fun getExecuteList() = executeList
    fun setExecuteList(executeList: MutableList<Card>) {
        this.executeList = executeList
    }

    fun getConfirmCount() = confirmCount
    fun setConfirmCount(count: Int) {
        this.confirmCount = count
    }

    fun vote(playerId: String, targetId: String) {
        cardList.forEachIndexed { i: Int, it: Card ->
            if (it.owner?.id == playerId) cardList[i].vote = targetId
        }
    }

    fun findPositionById(id: String?): Card? {
        for (p in getCardList())
            if (p.owner?.id == id)
                return p
        return null
    }

    fun findTruePositionById(id: String?): Card? {
        for (p in getCardList())
            if (p.trueOwner?.id == id)
                return p
        return null
    }
}