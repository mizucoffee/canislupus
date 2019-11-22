package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StartDiscussionViewModel : ViewModel() {
    val transition = MutableLiveData<Int>()

    fun next() {
        transition.postValue(0)
    }
}
