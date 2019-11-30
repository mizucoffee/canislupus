package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayerQrViewModel : ViewModel() {
    val alert = MutableLiveData<Int>()

    fun newAccount() {
        alert.postValue(0)
    }
}
