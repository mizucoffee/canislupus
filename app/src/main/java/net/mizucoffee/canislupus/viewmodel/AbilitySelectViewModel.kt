package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AbilitySelectViewModel : ViewModel() {
    val transition = MutableLiveData<Boolean>()

    fun next(confirmCount: Int, playerCount: Int) {
        transition.postValue( confirmCount < playerCount )
    }
}
