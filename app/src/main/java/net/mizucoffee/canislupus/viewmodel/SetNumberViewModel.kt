package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.werewolf.Position

class SetNumberViewModel : ViewModel() {
    val positionList = MutableLiveData<ArrayList<Position>>()
    val transitionLiveData = MutableLiveData<String>()

    fun initPosition(players: ArrayList<Player>) {
        val posList: ArrayList<Position> = ArrayList()
        Position.defaultSet[players.size]?.forEach { (k, v) ->
            for (i in 1..v) {
                val pos = Position.positionInit[k]?.invoke()
                if(pos != null) posList.add(pos)
            }
        }
        posList.shuffle()
        posList.forEachIndexed { i, e -> if(players.size > i) e.player = players[i] }
        positionList.postValue(posList)
    }

    fun next() {
        transitionLiveData.postValue("s")
    }
}
