package net.mizucoffee.canislupus.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_phase5.*
import kotlinx.android.synthetic.main.fragment_phase5.ability
import kotlinx.android.synthetic.main.fragment_phase5.cardList
import kotlinx.android.synthetic.main.fragment_phase5.result
import kotlinx.android.synthetic.main.fragment_phase5.sub
import kotlinx.android.synthetic.main.fragment_phase5.vote
import kotlinx.android.synthetic.main.fragment_result.*
import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.model.viewer.Phase5

class ViewerPhase5Fragment(val data: String) : Fragment() {

    companion object {
        fun newInstance(data: String) = ViewerPhase5Fragment(data)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_phase5, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val p5 = Gson().fromJson<Phase5>(data, Phase5::class.java)
        result.text = p5.winner
        sub.text = p5.sub
        if (p5.sub == "") sub.visibility = View.GONE

        p5.voteList.forEach {
            val tv = TextView(activity)
            tv.apply {
                text = it
                textSize = 32f
                setPadding(dp2px(0), dp2px(8), dp2px(0), dp2px(0))
            }
            vote.addView(tv)
        }
        p5.cardList.forEach {
            val tv = TextView(activity)
            tv.apply {
                text = it
                textSize = 32f
                setPadding(dp2px(0), dp2px(8), dp2px(0), dp2px(0))
            }
            cardList.addView(tv)
        }
        p5.abilityList.forEach {
            val tv = TextView(activity)
            tv.apply {
                text = it
                textSize = 24f
                setPadding(dp2px(0), dp2px(8), dp2px(0), dp2px(0))
            }
            ability.addView(tv)
        }

    }
    private fun dp2px(dp: Int) = (dp * resources.displayMetrics.density + 0.5f).toInt()
}
