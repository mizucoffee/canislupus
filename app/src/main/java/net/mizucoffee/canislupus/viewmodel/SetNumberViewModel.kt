package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.mizucoffee.canislupus.GameActivity
import net.mizucoffee.canislupus.werewolf.Position

class SetNumberViewModel : ViewModel() {
    val positionList = MutableLiveData<ArrayList<Position>>()

    fun initPosition(playerCount: Int) {
        val posList: ArrayList<Position> = ArrayList()
        Position.defaultSet[playerCount]?.forEach { (k, v) ->
            for (i in 1..v) {
                val pos = Position.positionInit[k]?.invoke()
                if(pos != null) posList.add(pos)
            }
        }
        positionList.postValue(posList)
    }
}
