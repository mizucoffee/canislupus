package net.mizucoffee.canislupus.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_discussion.*

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.activity.GameActivity
import net.mizucoffee.canislupus.databinding.FragmentDiscussionBinding
import net.mizucoffee.canislupus.viewmodel.DiscussionViewModel

class DiscussionFragment : Fragment() {

    companion object {
        fun newInstance() = DiscussionFragment()
    }

    private fun getGVM() = (activity as GameActivity).gameViewModel
    private lateinit var binding: FragmentDiscussionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        binding = FragmentDiscussionBinding.inflate(inflater, container, false)
        binding.viewModel = ViewModelProviders.of(this).get(DiscussionViewModel::class.java)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel?.startTimer()
        binding.viewModel?.also { observeTransition(it) }
    }

    private fun observeTransition(viewModel: DiscussionViewModel) {
        viewModel.transition.observe(this, Observer {
            getGVM().setConfirmCount(0)
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.gameFragmentLayout, CheckPlayerFragment.newInstance())
                ?.commit()
        })
    }
}
