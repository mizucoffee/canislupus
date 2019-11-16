package net.mizucoffee.canislupus.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.set_number_fragment.*
import net.mizucoffee.canislupus.GameActivity

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.viewmodel.SetNumberViewModel
import net.mizucoffee.canislupus.werewolf.Position

class SetNumberFragment : Fragment() {

    companion object {
        fun newInstance() = SetNumberFragment()
    }

    private lateinit var setNumberViewModel: SetNumberViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.set_number_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setNumberViewModel = ViewModelProviders.of(this).get(SetNumberViewModel::class.java)

        listenNextBtn(setNumberViewModel)
        observePositionList(setNumberViewModel)
    }

    fun listenNextBtn(viewModel: SetNumberViewModel) {
        nextBtn.setOnClickListener {
            viewModel.initPosition((activity as GameActivity).gameViewModel.getPlayers().size)
        }
    }

    fun observePositionList(viewModel: SetNumberViewModel) {
        viewModel.positionList.observe(this, Observer {
            (activity as GameActivity).gameViewModel.setPositionList(it)
        })
    }
}