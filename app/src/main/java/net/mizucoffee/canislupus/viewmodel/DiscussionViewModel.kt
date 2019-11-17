package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.concurrent.schedule

class DiscussionViewModel : ViewModel() {
    val count = MutableLiveData<Int>()
    val countFinished = MutableLiveData<Int>()
    val transition = MutableLiveData<String>()

    init {
        count.value = 60 * 3
    }
    fun next() {
        transition.postValue( "s" )
    }

    fun startTimer() {
        Timer().schedule(1000, 1000) {
            count.value?.let {
                count.postValue(it - 1)
                if(it - 1 <= 0) {
                    this.cancel()
                    countFinished.postValue(0)
                }
            }
        }
    }
}
