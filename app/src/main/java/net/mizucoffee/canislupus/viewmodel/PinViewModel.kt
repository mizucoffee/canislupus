package net.mizucoffee.canislupus.viewmodel

import android.view.View
import android.widget.Button
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PinViewModel : ViewModel() {
    val pin = MutableLiveData<String>()

    fun onClick(v: View) {
        val newPin = (pin.value ?: "") + (v as Button).text
        if(newPin.length > 4) return
        pin.postValue(newPin)
    }
}
