package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShowPositionViewModel : ViewModel() {
    val transition = MutableLiveData<Boolean>()

    fun next(confirmCount: Int, playerCount: Int) {
        println(confirmCount)
        println(playerCount)
        transition.postValue( confirmCount < playerCount )
    }
}
