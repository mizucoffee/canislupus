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
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.title = "canislupus - 人数設定"
        positionRecycler.apply {
            layoutManager = LinearLayoutManager(activity?.applicationContext)
            adapter = PositionAdapter(getGVM().getPlayers().size)
        }

        binding.viewModel?.also {
            observeCardList(it)
            observeTransition(it)
        }
    }

    private fun observeCardList(viewModel: SetNumberViewModel) {
        viewModel.cardList.observe(this, Observer {
            getGVM().setCardList(it)
        })
    }

    private fun observeTransition(viewModel: SetNumberViewModel) {
        viewModel.transition.observe(this, Observer {
            viewModel.initCard(
                (positionRecycler.adapter as PositionAdapter).countList,
                getGVM().getPlayers()
            )
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.gameFragmentLayout, ConfirmCardFragment.newInstance())
                ?.commit()
        })
    }
}