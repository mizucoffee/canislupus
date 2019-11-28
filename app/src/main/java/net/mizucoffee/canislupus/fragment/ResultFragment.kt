package net.mizucoffee.canislupus.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import androidx.core.view.marginTop
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_result.*

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.activity.GameActivity
import net.mizucoffee.canislupus.databinding.FragmentResultBinding
import net.mizucoffee.canislupus.viewmodel.ResultViewModel
import net.mizucoffee.canislupus.model.werewolf.Card

class ResultFragment : Fragment() {

    companion object {
        fun newInstance() = ResultFragment()
    }

    private fun getGVM() = (activity as GameActivity).gameViewModel
    private lateinit var binding: FragmentResultBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        binding = FragmentResultBinding.inflate(inflater, container, false)
        binding.viewModel = ViewModelProviders.of(this).get(ResultViewModel::class.java)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.title = "canislupus - 結果表示"
        binding.viewModel = ViewModelProviders.of(this).get(ResultViewModel::class.java)

        val winner = Card.checkWinner(getGVM().getExecuteList(), getGVM().getCardList())
        result.text = winner.result
        sub.text = winner.sub
        if (winner.sub == "") sub.visibility = GONE

        getGVM().getCardList().forEach { card ->
            card.vote?.let {
                val tv = TextView(activity)
                tv.apply {
                    text = "${card.owner?.name} -> ${getGVM().findPositionById(it)?.owner?.name}"
                    textSize = 24f
                    setPadding(dp2px(0), dp2px(8), dp2px(0), dp2px(0))
                }
                vote.addView(tv)
            }
        }

        getGVM().getCardList().forEach { card ->
            card.owner?.let {
                val tv = TextView(activity)
                tv.apply {
                    val trueCard = "${getGVM().findTruePositionById(it.id)?.name}"
                    text = if(card.name != trueCard)
                        "${it.name}: ${card.name} -> $trueCard"
                    else
                        "${it.name}: ${card.name}"
                    textSize = 24f
                    setPadding(dp2px(0), dp2px(8), dp2px(0), dp2px(0))
                }
                cardList.addView(tv)
            }
        }

        getGVM().getCardList().filter { it.abilityResultText() != null }.forEach { card ->
            val tv = TextView(activity)
            tv.apply {
                text = card.abilityResultText()
                textSize = 24f
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                setPadding(dp2px(0), dp2px(8), dp2px(0), dp2px(0))
            }
            ability.addView(tv)
        }

        binding.viewModel?.also { observeTransition(it) }
    }

    private fun dp2px(dp: Int) = (dp * resources.displayMetrics.density + 0.5f).toInt()

    private fun observeTransition(viewModel: ResultViewModel) {
        viewModel.transition.observe(this, Observer {
            activity?.finish()
        })
    }
}
