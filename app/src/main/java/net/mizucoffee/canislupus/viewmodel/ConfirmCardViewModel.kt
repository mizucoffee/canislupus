package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

class ConfirmCardViewModel : ViewModel() {
    val transition = MutableLiveData<Int>()
    var name = ""

    fun next() {
        transition.postValue(0)
    }
}
