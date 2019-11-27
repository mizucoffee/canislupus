package net.mizucoffee.canislupus.fragment

import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.activity.GameActivity
import android.widget.LinearLayout
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_vote.*
import net.mizucoffee.canislupus.databinding.FragmentVoteBinding
import net.mizucoffee.canislupus.viewmodel.VoteViewModel

class VoteFragment : Fragment() {

    companion object {
        fun newInstance() = VoteFragment()
    }

    private fun getGVM() = (activity as GameActivity).gameViewModel
    private lateinit var binding: FragmentVoteBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        binding = FragmentVoteBinding.inflate(inflater, container, false)
        binding.viewModel = ViewModelProviders.of(this).get(VoteViewModel::class.java)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.title = "canislupus - 処刑投票"
        val count = getGVM().getConfirmCount()
        val cards = getGVM().getCardList()
        val myCard = cards[count]

        val list = cards.filter { it != myCard }.filter { it.owner != null }

        list.forEach { card ->
            val btn = Button(context).apply {
                text = card.owner?.name
                textSize = 18f
                setTextColor(Color.WHITE)
                layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                    setMargins(0, dp2px(16), 0, 0)
                }
                setPadding(dp2px(32), dp2px(16), dp2px(32), dp2px(16))
                setBackgroundResource(R.drawable.bottom_button)
                setOnClickListener {
                    val player = myCard.owner
                    val target = card.owner

                    if (target != null && player != null) getGVM().vote(player.id, target.id)
                    getGVM().setConfirmCount(getGVM().getConfirmCount() + 1)
                    transition()
                }
            }
            voteList.addView(btn)
        }
    }

    private fun dp2px(dp: Int) = (dp * resources.displayMetrics.density + 0.5f).toInt()

    fun transition() {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        val next = if (getGVM().getConfirmCount() < getGVM().getPlayers().size) {
            CheckPlayerFragment.newInstance()
        } else {
            (activity as GameActivity).gameViewModel.setConfirmCount(0)
            PunishmentFragment.newInstance()
        }

        transaction?.replace(R.id.gameFragmentLayout, next)?.commit()
    }
}
