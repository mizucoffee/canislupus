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
import net.mizucoffee.canislupus.viewmodel.StartDiscussionViewModel

class StartDiscussionFragment : Fragment() {

    companion object {
        fun newInstance() = StartDiscussionFragment()
    }

    private lateinit var startDiscussionViewModel: StartDiscussionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start_discussion, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        startDiscussionViewModel = ViewModelProviders.of(this).get(StartDiscussionViewModel::class.java)

        listenNextBtn(startDiscussionViewModel)
        observeTransition(startDiscussionViewModel)
    }

    fun listenNextBtn(viewModel: StartDiscussionViewModel) {
        startBtn.setOnClickListener {
            (activity as GameActivity).gameViewModel.setConfirmCount((activity as GameActivity).gameViewModel.getConfirmCount() + 1)
            viewModel.next()
        }
    }

    fun observeTransition(viewModel: StartDiscussionViewModel) {
        viewModel.transition.observe(this, Observer {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.gameFragmentLayout, DiscussionFragment.newInstance())?.commit()
        })
    }
}
