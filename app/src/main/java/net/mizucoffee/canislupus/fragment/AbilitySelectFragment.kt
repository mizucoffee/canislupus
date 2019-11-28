package net.mizucoffee.canislupus.fragment

import android.content.DialogInterface
import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.fragment_vote.*

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.activity.GameActivity
import net.mizucoffee.canislupus.databinding.FragmentAbilitySelectBinding
import net.mizucoffee.canislupus.viewmodel.AbilitySelectViewModel

class AbilitySelectFragment : Fragment() {

    companion object {
        fun newInstance() = AbilitySelectFragment()
    }

    private fun getGVM() = (activity as GameActivity).gameViewModel
    private lateinit var binding: FragmentAbilitySelectBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        binding = FragmentAbilitySelectBinding.inflate(inflater, container, false)
        binding.viewModel = ViewModelProviders.of(this).get(AbilitySelectViewModel::class.java)
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun dp2px(dp: Int) = (dp * resources.displayMetrics.density + 0.5f).toInt()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.title = "canislupus - 役職確認"
        val count = getGVM().getConfirmCount()
        val cards = getGVM().getCardList()
        val card = cards[count]

        binding.viewModel?.message = card.getSelectMessage()

        card.getSelectList(cards)?.map { data ->
            voteList.addView(Button(context).apply {
                text = data.value
                layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                    topMargin = dp2px(16)
                    gravity = Gravity.CENTER
                }
                setPadding(dp2px(32), dp2px(16), dp2px(32), dp2px(16))
                setBackgroundResource(R.drawable.bottom_button)
                textSize = 18f
                setTextColor(Color.WHITE)
                setOnClickListener {
                    activity?.let { context ->
                        getGVM().setCardList(card.ability(getGVM().getCardList(), data.key))
                        AlertDialog.Builder(context)
                            .setTitle("結果")
                            .setView(
                                card.abilityResultView(getGVM().getCardList(), data.key, context)
                            )
                            .setPositiveButton("OK") { _: DialogInterface, _: Int ->
                                getGVM().setConfirmCount(getGVM().getConfirmCount() + 1)
                                val transaction =
                                    activity?.supportFragmentManager?.beginTransaction()
                                val next =
                                    if (getGVM().getConfirmCount() < getGVM().getPlayers().size) {
                                        ConfirmCardFragment.newInstance()
                                    } else {
                                        getGVM().setConfirmCount(0)
                                        StartDiscussionFragment.newInstance()
                                    }
                                transaction?.replace(R.id.gameFragmentLayout, next)?.commit()
                            }
                            .setCancelable(false)
                            .show()
                    }
                }
            })
        }
    }
}
