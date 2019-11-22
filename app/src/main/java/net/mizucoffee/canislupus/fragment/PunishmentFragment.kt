package net.mizucoffee.canislupus.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_punishment.*

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.activity.GameActivity
import net.mizucoffee.canislupus.databinding.FragmentPunishmentBinding
import net.mizucoffee.canislupus.viewmodel.PunishmentViewModel
import net.mizucoffee.canislupus.model.werewolf.Card

class PunishmentFragment : Fragment() {

    companion object {
        fun newInstance() = PunishmentFragment()
    }

    private fun getGVM() = (activity as GameActivity).gameViewModel
    private lateinit var binding: FragmentPunishmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        binding = FragmentPunishmentBinding.inflate(inflater, container, false)
        binding.viewModel = ViewModelProviders.of(this).get(PunishmentViewModel::class.java)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var max = 0
        var punishList: MutableList<Card> = mutableListOf()

        getGVM().getPlayers().forEach { player ->
            val count = getGVM().getCardList().count { it.vote == player.id }

            val position = getGVM().findTruePositionById(player.id)
            if (max == count) position?.let { punishList.add(it) }
            if (max < count) {
                max = count
                position?.let { punishList = mutableListOf(it) }
            }
        }
        if (punishList.size == getGVM().getPlayers().size) punishList.clear()
        today.text = if (punishList.isEmpty()) "本日は" else "本日処刑されたのは"
        punish.text =
            if (punishList.isEmpty())
                "誰も処刑\nされません"
            else
                "${punishList.map { it.trueOwner?.name }.joinToString("さん \r")}さん"

        getGVM().setExecuteList(punishList)

        binding.viewModel?.also { observeTransition(it) }
    }


    private fun observeTransition(viewModel: PunishmentViewModel) {
        viewModel.transition.observe(this, Observer {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.gameFragmentLayout, ResultFragment.newInstance())?.commit()
        })
    }
}