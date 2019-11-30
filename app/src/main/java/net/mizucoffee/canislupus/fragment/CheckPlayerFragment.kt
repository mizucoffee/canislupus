package net.mizucoffee.canislupus.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_confirm_card.*
import net.mizucoffee.canislupus.activity.GameActivity

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.databinding.FragmentCheckPlayerBinding
import net.mizucoffee.canislupus.repository.CanislupusService
import net.mizucoffee.canislupus.repository.simpleCall
import net.mizucoffee.canislupus.viewmodel.CheckPlayerViewModel

class CheckPlayerFragment : Fragment() {

    companion object {
        fun newInstance() = CheckPlayerFragment()
    }

    private fun getGVM() = (activity as GameActivity).gameViewModel
    private lateinit var binding: FragmentCheckPlayerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        binding = FragmentCheckPlayerBinding.inflate(inflater, container, false)
        binding.viewModel = ViewModelProviders.of(this).get(CheckPlayerViewModel::class.java)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.title = "canislupus - 処刑投票"
        binding.viewModel?.name = getGVM().getPlayers()[getGVM().getConfirmCount()].name
        binding.viewModel?.also { observeTransition(it) }

        CanislupusService.createService()
            .postGame("${getGVM().gameId.value}", 3, "{\"name\": \"${getGVM().getPlayers()[getGVM().getConfirmCount()].name}\"}")
            .simpleCall()
    }

    private fun observeTransition(viewModel: CheckPlayerViewModel) {
        viewModel.transition.observe(this, Observer {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.gameFragmentLayout, VoteFragment.newInstance())?.commit()
        })
    }
}
