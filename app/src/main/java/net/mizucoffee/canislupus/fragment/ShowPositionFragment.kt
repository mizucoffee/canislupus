package net.mizucoffee.canislupus.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_show_position.*

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.activity.GameActivity
import net.mizucoffee.canislupus.viewmodel.ShowPositionViewModel
import net.mizucoffee.canislupus.werewolf.Position
import android.content.DialogInterface

class ShowPositionFragment : Fragment() {

    companion object {
        fun newInstance() = ShowPositionFragment()
    }

    private lateinit var showPositionViewModel: ShowPositionViewModel

    fun getGVM() = (activity as GameActivity).gameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_show_position, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showPositionViewModel = ViewModelProviders.of(this).get(ShowPositionViewModel::class.java)

        val count = getGVM().getConfirmCount()
        val positions = getGVM().getPositionList()
        val pos = positions[count]

        camp.text = pos.camp.campName
        position.text = pos.name
        if (pos.getMiniMessage(positions) == null) miniDesctiption.visibility = View.GONE
        miniDesctiption.text = pos.getMiniMessage(positions)

        listenDescriptionBtn(pos)
        listenNextBtn(showPositionViewModel, pos)
        observeTransition(showPositionViewModel)
    }

    fun listenDescriptionBtn(pos: Position) {
        aboutPosition.setOnClickListener {
            activity?.let {
                AlertDialog.Builder(it)
                    .setTitle(pos.name)
                    .setMessage(pos.description)
                    .setPositiveButton("OK", null)
                    .show()
            }
        }
    }

    fun listenNextBtn(viewModel: ShowPositionViewModel, pos: Position) {
        nextBtn.setOnClickListener {
            if (pos.hasAbility() && !pos.shouldSelectList()) {
                getGVM().setPositionList(pos.ability(getGVM().getPositionList(), ""))
                activity?.let {
                    AlertDialog.Builder(it)
                        .setTitle("結果")
                        .setView(pos.abilityResult(getGVM().getPositionList(), "", it))
                        .setPositiveButton("OK") { dialogInterface: DialogInterface, i: Int ->
                            viewModel.next(
                                pos,
                                getGVM().getConfirmCount(),
                                getGVM().getPlayers().size
                            )
                        }
                        .setCancelable(false)
                        .show()
                }
            } else {
                viewModel.next(pos, getGVM().getConfirmCount(), getGVM().getPlayers().size)
            }
        }
    }

    fun observeTransition(viewModel: ShowPositionViewModel) {
        viewModel.transition.observe(this, Observer {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            val next = when (it) {
                0 -> AbilitySelectFragment.newInstance()
                1 -> {
                    getGVM().setConfirmCount(getGVM().getConfirmCount() + 1)
                    ConfirmPositionFragment.newInstance()
                }
                else -> {
                    getGVM().setConfirmCount(0)
                    StartDiscussionFragment.newInstance()
                }
            }
            transaction?.replace(R.id.gameFragmentLayout, next)?.commit()
        })
    }
}
