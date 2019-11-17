package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.mizucoffee.canislupus.werewolf.Position

class VoteViewModel : ViewModel() {
    val transition = MutableLiveData<Boolean>()

    fun next(confirmCount: Int, playerCount: Int) {
        transition.postValue( confirmCount < playerCount )
    }
    fun vote(position: Position) {

    }
}
