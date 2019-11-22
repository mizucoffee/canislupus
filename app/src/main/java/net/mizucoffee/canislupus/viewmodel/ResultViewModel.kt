package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResultViewModel : ViewModel() {
    val transition = MutableLiveData<Int>()

    fun next() {
        transition.postValue(0)
    }
}
