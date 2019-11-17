package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.mizucoffee.canislupus.werewolf.Position

class ShowPositionViewModel : ViewModel() {
    val transition = MutableLiveData<Int>()

    fun next(pos: Position, confirmCount: Int, playerCount: Int) {
        transition.postValue(when {
            pos.hasAbility() && pos.shouldSelectList() -> 0
            confirmCount + 1 < playerCount -> 1
            else -> 2
        })
    }
}
