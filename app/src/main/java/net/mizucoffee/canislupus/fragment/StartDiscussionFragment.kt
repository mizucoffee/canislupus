package net.mizucoffee.canislupus.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_start_discussion.*

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.activity.GameActivity
import net.mizucoffee.canislupus.databinding.FragmentStartDiscussionBinding
import net.mizucoffee.canislupus.viewmodel.StartDiscussionViewModel

class StartDiscussionFragment : Fragment() {

    companion object {
        fun newInstance() = StartDiscussionFragment()
    }

    private fun getGVM() = (activity as GameActivity).gameViewModel
    private lateinit var binding: FragmentStartDiscussionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        binding = FragmentStartDiscussionBinding.inflate(inflater, container, false)
        binding.viewModel = ViewModelProviders.of(this).get(StartDiscussionViewModel::class.java)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel?.also { observeTransition(it) }
    }


    fun observeTransition(viewModel: StartDiscussionViewModel) {
        viewModel.transition.observe(this, Observer {
            getGVM().setConfirmCount(getGVM().getConfirmCount() + 1)
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.gameFragmentLayout, DiscussionFragment.newInstance())
                ?.commit()
        })
    }
}
