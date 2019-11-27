package net.mizucoffee.canislupus.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.activity.GameActivity
import net.mizucoffee.canislupus.viewmodel.ShowPositionViewModel
import net.mizucoffee.canislupus.model.werewolf.Card
import android.content.DialogInterface
import net.mizucoffee.canislupus.databinding.FragmentShowCardBinding

class ShowCardFragment : Fragment() {

    companion object {
        fun newInstance() = ShowCardFragment()
    }

    private fun getGVM() = (activity as GameActivity).gameViewModel
    private lateinit var binding: FragmentShowCardBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        binding = FragmentShowCardBinding.inflate(inflater, container, false)
        binding.viewModel = ViewModelProviders.of(this).get(ShowPositionViewModel::class.java)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.title = "canislupus - 役職確認"
        val count = getGVM().getConfirmCount()
        val positions = getGVM().getCardList()
        binding.viewModel?.card = positions[count]
        binding.viewModel?.miniMessage = positions[count].getMiniMessage(positions)

        binding.viewModel?.also {
            observeAboutPosition(it, positions[count])
            observeTransition(it, positions[count])
        }
    }

    private fun observeAboutPosition(viewModel: ShowPositionViewModel, card: Card) {
        viewModel.aboutCard.observe(this, Observer {
            activity?.also {
                AlertDialog.Builder(it).apply {
                    setTitle(card.name)
                    setMessage(card.description)
                    setPositiveButton("OK", null)
                    show()
                }
            }
        })
    }

    private fun observeTransition(viewModel: ShowPositionViewModel, card: Card) {
        viewModel.transition.observe(this, Observer {
            if (card.hasAbility() && !card.shouldSelectList()) {
                getGVM().setCardList(card.ability(getGVM().getCardList(), ""))
                activity?.let {
                    AlertDialog.Builder(it)
                        .setTitle("結果")
                        .setView(card.abilityResult(getGVM().getCardList(), "", it))
                        .setPositiveButton("OK") { _: DialogInterface, _: Int -> transition(card) }
                        .setCancelable(false)
                        .show()
                }
            } else {
                transition(card)
            }
        })
    }

    fun transition(card: Card) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        val next = when {
            card.hasAbility() && card.shouldSelectList() -> AbilitySelectFragment.newInstance()
            getGVM().getConfirmCount() + 1 < getGVM().getPlayers().size -> {
                getGVM().setConfirmCount(getGVM().getConfirmCount() + 1)
                ConfirmCardFragment.newInstance()
            }
            else -> {
                getGVM().setConfirmCount(0)
                StartDiscussionFragment.newInstance()
            }
        }
        transaction?.replace(R.id.gameFragmentLayout, next)?.commit()
    }
}
