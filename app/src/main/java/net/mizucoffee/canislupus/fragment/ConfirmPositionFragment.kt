package net.mizucoffee.canislupus.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.confirm_player_fragment.*
import net.mizucoffee.canislupus.activity.GameActivity

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.viewmodel.ConfirmPositionViewModel

class ConfirmPositionFragment : Fragment() {

    companion object {
        fun newInstance(): ConfirmPositionFragment {
            return ConfirmPositionFragment()
        }
    }

    private lateinit var confirmPositionViewModel: ConfirmPositionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.confirm_player_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        confirmPositionViewModel = ViewModelProviders.of(this).get(ConfirmPositionViewModel::class.java)

        val players = (activity as GameActivity).gameViewModel.getPlayers()
        val count = (activity as GameActivity).gameViewModel.getConfirmCount()
        playerName.text = players[count].name

        listenNextBtn(confirmPositionViewModel)
        observeTransition(confirmPositionViewModel)
    }

    fun listenNextBtn(viewModel: ConfirmPositionViewModel) {
        nextBtn.setOnClickListener {
            viewModel.next()
        }
    }

    fun observeTransition(viewModel: ConfirmPositionViewModel) {
        viewModel.transition.observe(this, Observer {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.gameFragmentLayout, ShowPositionFragment.newInstance())?.commit()
        })
    }

}
