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
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
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
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val count = getGVM().getConfirmCount()
        val positions = getGVM().getPositionList()
        val pos = positions[count]

        binding.viewModel?.message = pos.getSelectMessage()
        val scale = resources.displayMetrics.density

        pos.getSelectList(positions)?.map { data ->
            voteList.addView(Button(context).apply {
                text = data.value
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    topMargin = (16 * scale + 0.5f).toInt()
                    gravity = Gravity.CENTER
                }
                setPadding(
                    (32 * scale + 0.5f).toInt(),
                    (16 * scale + 0.5f).toInt(),
                    (32 * scale + 0.5f).toInt(),
                    (16 * scale + 0.5f).toInt()
                )
                setBackgroundResource(R.drawable.bottom_button)
                textSize = 18f
                setTextColor(Color.WHITE)
                setOnClickListener {
                    activity?.let { context ->
                        getGVM().setPositionList(pos.ability(getGVM().getPositionList(), data.key))
                        AlertDialog.Builder(context)
                            .setTitle("結果")
                            .setView(pos.abilityResult(getGVM().getPositionList(), data.key, context))
                            .setPositiveButton("OK") { _: DialogInterface, _: Int ->
                                getGVM().setConfirmCount((activity as GameActivity).gameViewModel.getConfirmCount() + 1)
                                val transaction = activity?.supportFragmentManager?.beginTransaction()
                                val next = if(getGVM().getConfirmCount() < getGVM().getPlayers().size){
                                    ConfirmPositionFragment.newInstance()
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
