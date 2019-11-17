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
import net.mizucoffee.canislupus.viewmodel.AbilitySelectViewModel

class AbilitySelectFragment : Fragment() {

    companion object {
        fun newInstance() = AbilitySelectFragment()
    }

    private lateinit var viewModel: AbilitySelectViewModel

    fun getGVM() = (activity as GameActivity).gameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vote, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AbilitySelectViewModel::class.java)

        val count = (activity as GameActivity).gameViewModel.getConfirmCount()
        val positions = (activity as GameActivity).gameViewModel.getPositionList()
        val pos = positions[count]

        message.text = pos.getSelectMessage()
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
                                (activity as GameActivity).gameViewModel.setConfirmCount((activity as GameActivity).gameViewModel.getConfirmCount() + 1)
                                viewModel.next(
                                    getGVM().getConfirmCount(),
                                    getGVM().getPlayers().size
                                )
                            }
                            .setCancelable(false)
                            .show()
                    }
                }
            })

        }

        observeTransition(viewModel)
    }

    fun observeTransition(viewModel: AbilitySelectViewModel) {
        viewModel.transition.observe(this, Observer {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            val next = if (it) {
                ConfirmPositionFragment.newInstance()
            } else {
                (activity as GameActivity).gameViewModel.setConfirmCount(0)
                StartDiscussionFragment.newInstance()
            }

            transaction?.replace(R.id.gameFragmentLayout, next)?.commit()
        })
    }
}
