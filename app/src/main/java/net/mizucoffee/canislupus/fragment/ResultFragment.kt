package net.mizucoffee.canislupus.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_result.*

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.activity.GameActivity
import net.mizucoffee.canislupus.databinding.FragmentResultBinding
import net.mizucoffee.canislupus.viewmodel.ResultViewModel
import net.mizucoffee.canislupus.model.werewolf.Card

class ResultFragment : Fragment() {

    companion object {
        fun newInstance() = ResultFragment()
    }

    private fun getGVM() = (activity as GameActivity).gameViewModel
    private lateinit var binding: FragmentResultBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        binding = FragmentResultBinding.inflate(inflater, container, false)
        binding.viewModel = ViewModelProviders.of(this).get(ResultViewModel::class.java)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = ViewModelProviders.of(this).get(ResultViewModel::class.java)

        val winner = Card.checkWinner(getGVM().getExecuteList(), getGVM().getCardList())
        result.text = winner.result
        sub.text = winner.sub

        binding.viewModel?.also { observeTransition(it) }
    }

    private fun observeTransition(viewModel: ResultViewModel) {
        viewModel.transition.observe(this, Observer {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.gameFragmentLayout, PlayerListFragment.newInstance())
                ?.commit()
        })
    }
}
