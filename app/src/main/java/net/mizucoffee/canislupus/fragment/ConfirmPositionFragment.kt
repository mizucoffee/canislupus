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
import net.mizucoffee.canislupus.databinding.FragmentConfirmPlayerBinding
import net.mizucoffee.canislupus.viewmodel.ConfirmPositionViewModel

class ConfirmPositionFragment : Fragment() {

    companion object {
        fun newInstance(): ConfirmPositionFragment {
            return ConfirmPositionFragment()
        }
    }

    private fun getGVM() = (activity as GameActivity).gameViewModel
    private lateinit var binding: FragmentConfirmPlayerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        binding = FragmentConfirmPlayerBinding.inflate(inflater, container, false)
        binding.viewModel = ViewModelProviders.of(this).get(ConfirmPositionViewModel::class.java)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel?.name = getGVM().getPlayers()[getGVM().getConfirmCount()].name
        binding.viewModel?.also {
            observeTransition(it)
        }
    }

    fun observeTransition(viewModel: ConfirmPositionViewModel) {
        viewModel.transition.observe(this, Observer {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.gameFragmentLayout, ShowPositionFragment.newInstance())
                ?.commit()
        })
    }

}
