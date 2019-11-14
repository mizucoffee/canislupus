package net.mizucoffee.canislupus.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.mizucoffee.canislupus.GameActivity

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.viewmodel.SetNumberViewModel

class SetNumberFragment : Fragment() {

    companion object {
        fun newInstance() = SetNumberFragment()
    }

    private lateinit var viewModel: SetNumberViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.set_number_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SetNumberViewModel::class.java)
        println((activity as GameActivity).gameViewModel.getPlayers())
    }

}
