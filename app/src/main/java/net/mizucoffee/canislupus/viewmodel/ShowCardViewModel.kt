package net.mizucoffee.canislupus.viewmodel

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.mizucoffee.canislupus.model.werewolf.Card

class ShowCardViewModel : ViewModel() {
    val transition = MutableLiveData<Int>()
    val aboutCard = MutableLiveData<Int>()
    val symbol = MutableLiveData<Drawable>()
    var card: Card? = null
    var miniMessage: String? = null

    fun next() {
        transition.postValue(0)
    }

    fun aboutCard() {
        aboutCard.postValue(0)
    }
}
