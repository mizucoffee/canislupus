package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.mizucoffee.canislupus.adapter.PlayerListAdapter
import net.mizucoffee.canislupus.model.Player

class PlayerListViewModel : ViewModel() {
    val players = MutableLiveData<MutableList<Player>>()
    val toast = MutableLiveData<String>()
    val transition = MutableLiveData<Int>()

    var adapter: PlayerListAdapter = PlayerListAdapter()
    var listener = object : PlayerListAdapter.OnItemClickListener {
        override fun onItemCLickListener(pos: Int, count: Int) {
            addPlayer(pos, count)
        }
    }

    fun addPlayer(pos: Int, playerCount: Int) {
        if (pos != playerCount - 1) return
        val list = adapter.players.toMutableList()
        val count = list.size.plus(1)
        list.add(Player("Player $count", "$count"))

        players.postValue(list)
        adapter.players = list
    }

    fun next() {
        val list = adapter.players.toMutableList()
        if(list.size < 3)
            toast.postValue("3人以上必要です")
        else
            transition.postValue(0)
    }
}