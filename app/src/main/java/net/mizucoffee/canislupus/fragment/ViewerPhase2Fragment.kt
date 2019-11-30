package net.mizucoffee.canislupus.fragment

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_phase2.*
import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.model.viewer.Phase2
import java.util.*
import kotlin.concurrent.schedule

class ViewerPhase2Fragment(val data: String) : Fragment() {

    private lateinit var timer: TimerTask

    companion object {
        fun newInstance(data: String) = ViewerPhase2Fragment(data)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_phase2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val p2 = Gson().fromJson<Phase2>(data, Phase2::class.java)

        val h = Handler()
        timer = Timer().schedule(100, 100) {
            val count = 180 - (System.currentTimeMillis() - p2.startTime) / 1000
            h.post {
                if(counter == null) {
                    this.cancel()
                } else {
                    counter.text = "${(count / 60).toString().padStart(2, '0')}" +
                            ":${(count % 60).toString().padStart(2, '0')}"
                }
            }

            if (count - 1 <= -1) {
                this.cancel()
                h.post {
                    if(counter != null) counter.text = "話し合い終了"
                }
            }
        }

        var a = 1
        p2.count.forEach { (name, count) ->
            if (count != 0) {
                val tv = TextView(activity).apply {
                    setText("$name: $count")
                    textSize = 48f
                    setTextColor(Color.parseColor("#AAAAAA"))
                    setPadding(dp2px(8),dp2px(2),dp2px(8),dp2px(2))
                }
                when (a % 3) {
                    0 -> cardList1.addView(tv)
                    1 -> cardList2.addView(tv)
                    2 -> cardList3.addView(tv)
                }
                a++
            }
        }
    }
    private fun dp2px(dp: Int) = (dp * resources.displayMetrics.density + 0.5f).toInt()

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}
