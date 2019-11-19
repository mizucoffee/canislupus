package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.mizucoffee.canislupus.enumerate.PositionEnum
import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.werewolf.Position

class SetNumberViewModel : ViewModel() {
    val positionList = MutableLiveData<ArrayList<Position>>()
    val truePositionList = MutableLiveData<ArrayList<Position>>()
    val transitionLiveData = MutableLiveData<String>()

    fun initPosition(playerCounts: Map<PositionEnum, Int>, players: List<Player>) {
        val posList: ArrayList<Position> = ArrayList()

        val l = mutableListOf<PositionEnum>()
        playerCounts.forEach { (k, v) -> for (i in 1..v) l.add(k) }
        l.shuffled().forEach { e ->
            Position.positionInit[e]?.invoke()?.also { posList.add(it) }
        }

        posList.forEachIndexed { i, e -> if (players.size > i) {
            e.player = players[i]
            e.truePlayer = players[i]
        } }
        positionList.postValue(posList)
    }

    fun next() {
        transitionLiveData.postValue("s")
    }
}
