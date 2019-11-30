package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.concurrent.schedule

class DiscussionViewModel : ViewModel() {
    val transition = MutableLiveData<Int>()
    var counterText = MutableLiveData<String>()
    var finished = false
    var startTime: Long = 0

    init {
        counterText.postValue(
            "${3.toString().padStart(2, '0')}" +
                    ":${0.toString().padStart(2, '0')}"
        )
    }

    fun next() {
        transition.postValue(0)
    }

    fun startTimer(startTime: Long) {
        this.startTime = startTime

        Timer().schedule(100, 100) {
            val count = 180 - (System.currentTimeMillis() - startTime) / 1000
            counterText.postValue(
                "${(count / 60).toString().padStart(2, '0')}" +
                        ":${(count % 60).toString().padStart(2, '0')}"
            )

            if (count - 1 <= -1) {
                this.cancel()
                counterText.postValue("話し合い終了")
            }
        }
    }
}
