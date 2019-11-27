package net.mizucoffee.canislupus.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import net.mizucoffee.canislupus.activity.GameActivity

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.databinding.FragmentConfirmCardBinding
import net.mizucoffee.canislupus.viewmodel.ConfirmCardViewModel

class ConfirmCardFragment : Fragment() {

    companion object {
        fun newInstance() = ConfirmCardFragment()
    }

    private fun getGVM() = (activity as GameActivity).gameViewModel
    private lateinit var binding: FragmentConfirmCardBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        binding = FragmentConfirmCardBinding.inflate(inflater, container, false)
        binding.viewModel = ViewModelProviders.of(this).get(ConfirmCardViewModel::class.java)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.title = "canislupus - 役職確認"
        binding.viewModel?.name = getGVM().getPlayers()[getGVM().getConfirmCount()].name
        binding.viewModel?.also { observeTransition(it) }
    }

    private fun observeTransition(viewModel: ConfirmCardViewModel) {
        viewModel.transition.observe(this, Observer {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.gameFragmentLayout, ShowCardFragment.newInstance())
                ?.commit()
        })
    }

}
