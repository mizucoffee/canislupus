package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.mizucoffee.canislupus.model.Player

class PlayerListViewModel : ViewModel() {
    val playerLiveData = MutableLiveData<ArrayList<Player>>()
    val toastLiveData = MutableLiveData<String>()
    val transitionLiveData = MutableLiveData<String>()

    fun addPlayer() {
        var list = playerLiveData.value
        if(list == null) list = ArrayList()
        val count = list.size.plus(1)
        list.add(Player("Player $count", "$count"))
        playerLiveData.postValue(list)
    }

    fun next() {
        var list = playerLiveData.value
        if(list == null) list = ArrayList()
        if(list.size < 3)
            toastLiveData.postValue("3人以上必要です")
        else
            transitionLiveData.postValue("s")
    }
}