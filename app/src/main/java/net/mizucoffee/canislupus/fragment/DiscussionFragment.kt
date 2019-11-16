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
    }

    fun startTimer(viewModel: DiscussionViewModel) {
        viewModel.startTimer()
    }

    fun observeTimer(viewModel: DiscussionViewModel) {
        viewModel.count.observe(this, Observer {
            counter.text = "${(it / 60).toString().padStart(2, '0')}:${(it % 60).toString().padStart(2, '0')}"
        })
    }

}
