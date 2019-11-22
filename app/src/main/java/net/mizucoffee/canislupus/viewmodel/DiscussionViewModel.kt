package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.concurrent.schedule

class DiscussionViewModel : ViewModel() {
    val transition = MutableLiveData<Int>()
    var count = 60 * 3
    var counterText = MutableLiveData<String>()
    var finished = false

    init {
        counterText.postValue(
            "${(count / 60).toString().padStart(2, '0')}" +
                    ":${(count % 60).toString().padStart(2, '0')}"
        )
    }

    fun next() {
        transition.postValue(0)
    }

    fun startTimer() {
        Timer().schedule(1000, 1000) {
            count -= 1
            counterText.postValue(
                "${(count / 60).toString().padStart(2, '0')}" +
                        ":${(count % 60).toString().padStart(2, '0')}"
            )
            if (count - 1 <= 0) {
                this.cancel()
                counterText.postValue("話し合い終了")
                finished = true
            }
        }
    }
}
