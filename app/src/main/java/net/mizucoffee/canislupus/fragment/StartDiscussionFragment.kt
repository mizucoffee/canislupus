package net.mizucoffee.canislupus.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.viewmodel.StartDiscussionViewModel

class StartDiscussionFragment : Fragment() {

    companion object {
        fun newInstance() = StartDiscussionFragment()
    }

    private lateinit var viewModel: StartDiscussionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.start_discussion_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(StartDiscussionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
