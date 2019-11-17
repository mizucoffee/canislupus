package net.mizucoffee.canislupus.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.common.collect.ImmutableList
import kotlinx.android.synthetic.main.fragment_punishment.*

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.activity.GameActivity
import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.viewmodel.PunishmentViewModel
import net.mizucoffee.canislupus.werewolf.Position

class PunishmentFragment : Fragment() {

    companion object {
        fun newInstance() = PunishmentFragment()
    }

    private lateinit var viewModel: PunishmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_punishment, container, false)
    }

    fun getGVM() = (activity as GameActivity).gameViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PunishmentViewModel::class.java)

        var max = 0
        var punishList: MutableList<Position> = mutableListOf()

        getGVM().getPlayers().forEach { player ->
            val count = getGVM().getPositionList().count { it.vote == player.id }

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
            if (punishList.isEmpty()) "誰も処刑\nされません" else "${punishList.map { it.truePlayer?.name }.joinToString(
                "さん \r"
            )}さん"

        getGVM().setExecuteList(punishList)
        listenNextBtn(viewModel)
        observeTransition(viewModel)
    }

    fun listenNextBtn(viewModel: PunishmentViewModel) {
        nextBtn.setOnClickListener {
            viewModel.next()
        }
    }

    fun observeTransition(viewModel: PunishmentViewModel) {
        viewModel.transition.observe(this, Observer {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.gameFragmentLayout, ResultFragment.newInstance())?.commit()
        })
    }
}