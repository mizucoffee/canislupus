package net.mizucoffee.canislupus.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_confirm_player.*
import net.mizucoffee.canislupus.activity.GameActivity

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.viewmodel.CheckPlayerViewModel
import net.mizucoffee.canislupus.viewmodel.ConfirmPositionViewModel

class CheckPlayerFragment : Fragment() {

    companion object {
        fun newInstance(): CheckPlayerFragment {
            return CheckPlayerFragment()
        }
    }

    private lateinit var checkPlayerViewModel: CheckPlayerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_confirm_player, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        checkPlayerViewModel = ViewModelProviders.of(this).get(CheckPlayerViewModel::class.java)

        val players = (activity as GameActivity).gameViewModel.getPlayers()
        val count = (activity as GameActivity).gameViewModel.getConfirmCount()
        playerName.text = players[count].name

        listenNextBtn(checkPlayerViewModel)
        observeTransition(checkPlayerViewModel)
    }

    fun listenNextBtn(viewModel: CheckPlayerViewModel) {
        nextBtn.setOnClickListener {
            viewModel.next()
        }
    }

    fun observeTransition(viewModel: CheckPlayerViewModel) {
        viewModel.transition.observe(this, Observer {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.gameFragmentLayout, VoteFragment.newInstance())?.commit()
        })
    }

}
