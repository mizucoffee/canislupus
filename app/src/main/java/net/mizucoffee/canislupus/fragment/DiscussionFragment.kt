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
import net.mizucoffee.canislupus.viewmodel.DiscussionViewModel

class DiscussionFragment : Fragment() {

    companion object {
        fun newInstance() = DiscussionFragment()
    }

    private lateinit var discussionViewModel: DiscussionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_discussion, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        discussionViewModel = ViewModelProviders.of(this).get(DiscussionViewModel::class.java)

        startTimer(discussionViewModel)
        observeTimer(discussionViewModel)
        observeTimerFinished(discussionViewModel)
        listenNextBtn(discussionViewModel)
        observeTransition(discussionViewModel)
    }

    fun startTimer(viewModel: DiscussionViewModel) {
        viewModel.startTimer()
    }

    fun observeTimer(viewModel: DiscussionViewModel) {
        viewModel.count.observe(this, Observer {
            counter.text = "${(it / 60).toString().padStart(2, '0')}:${(it % 60).toString().padStart(2, '0')}"
        })
    }

    fun observeTimerFinished(viewModel: DiscussionViewModel) {
        viewModel.countFinished.observe(this, Observer {
            counter.text = "話し合い終了"
            quiteBtn.isEnabled = false
            trueBtn.isEnabled = false
        })
    }

    fun listenNextBtn(viewModel: DiscussionViewModel) {
        nextBtn.setOnClickListener {
            (activity as GameActivity).gameViewModel.setConfirmCount(0)
            viewModel.next()
        }
    }

    fun observeTransition(viewModel: DiscussionViewModel) {
        viewModel.transition.observe(this, Observer {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.gameFragmentLayout, CheckPlayerFragment.newInstance())?.commit()
        })
    }
}
