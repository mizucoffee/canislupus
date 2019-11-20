package net.mizucoffee.canislupus.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_set_number.*
import net.mizucoffee.canislupus.activity.GameActivity

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.adapter.PositionAdapter
import net.mizucoffee.canislupus.databinding.FragmentSetNumberBinding
import net.mizucoffee.canislupus.viewmodel.SetNumberViewModel

class SetNumberFragment : Fragment() {

    companion object {
        fun newInstance() = SetNumberFragment()
    }

    private fun getGVM() = (activity as GameActivity).gameViewModel
    private lateinit var binding: FragmentSetNumberBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        binding = FragmentSetNumberBinding.inflate(inflater, container, false)
        binding.viewModel = ViewModelProviders.of(this).get(SetNumberViewModel::class.java)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        positionRecycler.apply {
            layoutManager = LinearLayoutManager(activity?.applicationContext)
            adapter = PositionAdapter(getGVM().getPlayers().size)
        }

        binding.viewModel?.also {
            observePositionList(it)
            observeTransition(it)
        }
    }

    fun observePositionList(viewModel: SetNumberViewModel) {
        viewModel.positionList.observe(this, Observer {
            getGVM().setPositionList(it)
        })
    }

    fun observeTransition(viewModel: SetNumberViewModel) {
        viewModel.transition.observe(this, Observer {
            viewModel.initPosition(
                (positionRecycler.adapter as PositionAdapter).countList,
                getGVM().getPlayers()
            )
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.gameFragmentLayout, ConfirmPositionFragment.newInstance())
                ?.commit()
        })
    }
}