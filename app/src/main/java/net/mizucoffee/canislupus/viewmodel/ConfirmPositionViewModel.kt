package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

class ConfirmPositionViewModel : ViewModel() {
    val transition = MutableLiveData<String>()

    fun next() {
        transition.postValue("s")
    }
}
