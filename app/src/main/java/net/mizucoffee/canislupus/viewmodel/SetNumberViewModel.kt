package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.mizucoffee.canislupus.enumerate.CardEnum
import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.model.werewolf.Card

class SetNumberViewModel : ViewModel() {
    val cardList = MutableLiveData<MutableList<Card>>()
    val transition = MutableLiveData<Int>()

    fun initCard(playerCounts: Map<CardEnum, Int>, players: List<Player>) {
        val list = mutableListOf<Card>()
        playerCounts.forEach { (k, v) -> for (i in 1..v) list.add(k.init()) }
        list.shuffle()
        list.forEachIndexed { i, e ->
            if (players.size > i) {
                e.owner = players[i]
                e.trueOwner = players[i]
            }
        }
        cardList.postValue(list)
    }

    fun next() {
        transition.postValue(0)
    }
}
