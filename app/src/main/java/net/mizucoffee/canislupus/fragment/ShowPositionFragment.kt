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

class ShowPositionFragment : Fragment() {

    companion object {
        fun newInstance() = ShowPositionFragment()
    }

    private lateinit var showPositionViewModel: ShowPositionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_show_position, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showPositionViewModel = ViewModelProviders.of(this).get(ShowPositionViewModel::class.java)

        val count = (activity as GameActivity).gameViewModel.getConfirmCount()
        val positions = (activity as GameActivity).gameViewModel.getPositionList()
        val pos = positions[count]

        camp.text = pos.camp.campName
        position.text = pos.position.positionName
        if (pos.getMiniMessage(positions) == null) miniDesctiption.visibility = View.GONE
        miniDesctiption.text = pos.getMiniMessage(positions)

        listenDescriptionBtn(pos)
        listenNextBtn(showPositionViewModel)
        observeTransition(showPositionViewModel)
    }

    fun listenDescriptionBtn(pos: Position) {
        aboutPosition.setOnClickListener {
            val con = activity
            con?.let {
                AlertDialog.Builder(con)
                    .setTitle(pos.position.positionName)
                    .setMessage(pos.position.description)
                    .setPositiveButton("OK", null)
                    .show()
            }
        }
    }

    fun listenNextBtn(viewModel: ShowPositionViewModel) {
        nextBtn.setOnClickListener {
            (activity as GameActivity).gameViewModel.setConfirmCount((activity as GameActivity).gameViewModel.getConfirmCount() + 1)
            viewModel.next((activity as GameActivity).gameViewModel.getConfirmCount(), (activity as GameActivity).gameViewModel.getPlayers().size)
        }
    }

    fun observeTransition(viewModel: ShowPositionViewModel) {
        viewModel.transition.observe(this, Observer {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            val next = if(it){
                ConfirmPositionFragment.newInstance()
            } else {
                (activity as GameActivity).gameViewModel.setConfirmCount(0)
                StartDiscussionFragment.newInstance()
            }

            transaction?.replace(R.id.gameFragmentLayout, next)?.commit()
        })
    }
}
