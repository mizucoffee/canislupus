package net.mizucoffee.canislupus.fragment

import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.activity.GameActivity
import android.widget.LinearLayout
import android.widget.Button
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_vote.*
import net.mizucoffee.canislupus.viewmodel.VoteViewModel

class VoteFragment : Fragment() {

    companion object {
        fun newInstance() = VoteFragment()
    }

    private lateinit var viewModel: VoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vote, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(VoteViewModel::class.java)

        val count = (activity as GameActivity).gameViewModel.getConfirmCount()
        val positions = (activity as GameActivity).gameViewModel.getPositionList()
        val pos = positions[count]

        message.text = "投票先を選択してください"
        val list = positions.filter { it != pos }.filter { it.player != null }
        val scale = resources.displayMetrics.density

        list.map { position ->
            val btn = Button(context)
            btn.text = position.player?.name

            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            lp.setMargins(0, (16 * scale + 0.5f).toInt(), 0, 0)
            btn.layoutParams = lp
            btn.setPadding(
                (32 * scale + 0.5f).toInt(),
                (16 * scale + 0.5f).toInt(),
                (32 * scale + 0.5f).toInt(),
                (16 * scale + 0.5f).toInt()
            )
            btn.setBackgroundResource(R.drawable.bottom_button)
            btn.textSize = 18f
            btn.setTextColor(Color.WHITE)
            btn.setOnClickListener {
                val player = pos.player
                val target = position.player

                if (target != null && player != null)
                    (activity as GameActivity).gameViewModel.vote(player.id, target.id)
                (activity as GameActivity).gameViewModel.setConfirmCount((activity as GameActivity).gameViewModel.getConfirmCount() + 1)
                viewModel.next(
                    (activity as GameActivity).gameViewModel.getConfirmCount(),
                    (activity as GameActivity).gameViewModel.getPlayers().size
                )
            }

            voteList.addView(btn)
        }

        observeTransition(viewModel)
    }

    fun observeTransition(viewModel: VoteViewModel) {
        viewModel.transition.observe(this, Observer {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            val next = if (it) {
                CheckPlayerFragment.newInstance()
            } else {
                (activity as GameActivity).gameViewModel.setConfirmCount(0)
                PunishmentFragment.newInstance()
            }

            transaction?.replace(R.id.gameFragmentLayout, next)?.commit()
        })
    }
}
