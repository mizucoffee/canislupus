package net.mizucoffee.canislupus.fragment

import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_discussion.*

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.activity.GameActivity
import net.mizucoffee.canislupus.databinding.FragmentDiscussionBinding
import net.mizucoffee.canislupus.enumerate.CardEnum
import net.mizucoffee.canislupus.viewmodel.DiscussionViewModel

class DiscussionFragment : Fragment() {

    companion object {
        fun newInstance() = DiscussionFragment()
    }

    private fun getGVM() = (activity as GameActivity).gameViewModel
    private lateinit var binding: FragmentDiscussionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        binding = FragmentDiscussionBinding.inflate(inflater, container, false)
        binding.viewModel = ViewModelProviders.of(this).get(DiscussionViewModel::class.java)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.title = "canislupus - 話し合い"
        var a = 1
        CardEnum.values().forEach { card ->
            val count = getGVM().getCardList().count { it.card == card }
            if (count != 0) {
                val tv = TextView(activity).apply {
                    setText("${card.init().name}: ${count}")
                    textSize = 24f
                    setTextColor(Color.parseColor("#AAAAAA"))
                    setPadding(dp2px(8),dp2px(2),dp2px(8),dp2px(2))
                }
                if (a % 2 != 0) cardList1.addView(tv) else cardList2.addView(tv)
                a++
                println(card.name)
            }
        }

        binding.viewModel?.startTimer()
        binding.viewModel?.also { observeTransition(it) }
    }

    private fun dp2px(dp: Int) = (dp * resources.displayMetrics.density + 0.5f).toInt()

    private fun observeTransition(viewModel: DiscussionViewModel) {
        viewModel.transition.observe(this, Observer {
            getGVM().setConfirmCount(0)
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.gameFragmentLayout, CheckPlayerFragment.newInstance())
                ?.commit()
        })
    }
}
