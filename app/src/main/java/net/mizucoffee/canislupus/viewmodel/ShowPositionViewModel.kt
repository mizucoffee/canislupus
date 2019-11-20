package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.mizucoffee.canislupus.werewolf.Position

class ShowPositionViewModel : ViewModel() {
    val transition = MutableLiveData<Int>()
    val aboutPosition = MutableLiveData<Int>()
    var position: Position? = null
    var miniMessage: String? = null

    fun next() {
        transition.postValue(0)
    }

    fun aboutPosition() {
        aboutPosition.postValue(0)
    }
}
