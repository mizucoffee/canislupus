package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.mizucoffee.canislupus.werewolf.Card

class ShowPositionViewModel : ViewModel() {
    val transition = MutableLiveData<Int>()
    val aboutCard = MutableLiveData<Int>()
    var card: Card? = null
    var miniMessage: String? = null

    fun next() {
        transition.postValue(0)
    }

    fun aboutCard() {
        aboutCard.postValue(0)
    }
}
