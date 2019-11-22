package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

class CheckPlayerViewModel : ViewModel() {
    val transition = MutableLiveData<Int>()
    var name: String? = null

    fun next() {
        transition.postValue(0)
    }
}
