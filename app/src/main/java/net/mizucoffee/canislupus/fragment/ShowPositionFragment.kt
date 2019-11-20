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
import net.mizucoffee.canislupus.databinding.FragmentShowPositionBinding

class ShowPositionFragment : Fragment() {

    companion object {
        fun newInstance() = ShowPositionFragment()
    }

    private fun getGVM() = (activity as GameActivity).gameViewModel
    private lateinit var binding: FragmentShowPositionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        binding = FragmentShowPositionBinding.inflate(inflater, container, false)
        binding.viewModel = ViewModelProviders.of(this).get(ShowPositionViewModel::class.java)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = ViewModelProviders.of(this).get(ShowPositionViewModel::class.java)

        val count = getGVM().getConfirmCount()
        val positions = getGVM().getPositionList()
        binding.viewModel?.position = positions[count]
        binding.viewModel?.miniMessage = positions[count].getMiniMessage(positions)

        binding.viewModel?.also {
            observeAboutPosition(it, positions[count])
            observeTransition(it, positions[count])
        }
    }

    private fun observeAboutPosition(viewModel: ShowPositionViewModel, pos: Position) {
        viewModel.aboutPosition.observe(this, Observer {
            activity?.also {
                AlertDialog.Builder(it).apply {
                    setTitle(pos.name)
                    setMessage(pos.description)
                    setPositiveButton("OK", null)
                    show()
                }
            }
        })
    }

    fun observeTransition(viewModel: ShowPositionViewModel, pos: Position) {
        viewModel.transition.observe(this, Observer {
            if (pos.hasAbility() && !pos.shouldSelectList()) {
                getGVM().setPositionList(pos.ability(getGVM().getPositionList(), ""))
                activity?.let {
                    AlertDialog.Builder(it)
                        .setTitle("結果")
                        .setView(pos.abilityResult(getGVM().getPositionList(), "", it))
                        .setPositiveButton("OK") { _: DialogInterface, _: Int -> transition(pos) }
                        .setCancelable(false)
                        .show()
                }
            } else {
                transition(pos)
            }
        })
    }

    fun transition(pos: Position) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        val next = when {
            pos.hasAbility() && pos.shouldSelectList() -> AbilitySelectFragment.newInstance()
            getGVM().getConfirmCount() + 1 < getGVM().getPlayers().size -> {
                getGVM().setConfirmCount(getGVM().getConfirmCount() + 1)
                ConfirmPositionFragment.newInstance()
            }
            else -> {
                getGVM().setConfirmCount(0)
                StartDiscussionFragment.newInstance()
            }
        }
        transaction?.replace(R.id.gameFragmentLayout, next)?.commit()
    }
}
