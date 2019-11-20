package net.mizucoffee.canislupus.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_player_list.*
import net.mizucoffee.canislupus.activity.GameActivity

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.databinding.FragmentPlayerListBinding
import net.mizucoffee.canislupus.viewmodel.PlayerListViewModel

class PlayerListFragment : Fragment() {

    companion object {
        fun newInstance() = PlayerListFragment()
    }

    private fun getGVM() = (activity as GameActivity).gameViewModel
    private lateinit var binding: FragmentPlayerListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        binding = FragmentPlayerListBinding.inflate(inflater, container, false)
        binding.viewModel = ViewModelProviders.of(this).get(PlayerListViewModel::class.java)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        playerList.layoutManager = LinearLayoutManager(activity?.applicationContext)
        playerList.addItemDecoration(
            DividerItemDecoration(playerList.context, DividerItemDecoration.VERTICAL)
        )

        binding.viewModel?.also {
            observePlayerList(it)
            observeToast(it)
            observeTransition(it)
        }
    }

    private fun observePlayerList(viewModel: PlayerListViewModel) {
        viewModel.players.observe(this, Observer { getGVM().setPlayers(it) })
    }

    private fun observeToast(viewModel: PlayerListViewModel) {
        viewModel.toast.observe(this, Observer {
            Toast.makeText(activity?.applicationContext, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun observeTransition(viewModel: PlayerListViewModel) {
        viewModel.transition.observe(this, Observer {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.gameFragmentLayout, SetNumberFragment.newInstance())?.commit()
        })
    }

}
