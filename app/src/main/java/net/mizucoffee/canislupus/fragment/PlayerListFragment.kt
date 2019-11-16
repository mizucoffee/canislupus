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
import kotlinx.android.synthetic.main.player_list_fragment.*
import net.mizucoffee.canislupus.activity.GameActivity

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.adapter.PlayerListAdapter
import net.mizucoffee.canislupus.viewmodel.PlayerListViewModel

class PlayerListFragment : Fragment() {

    companion object {
        fun newInstance() = PlayerListFragment()
    }

    private lateinit var playerListAdapter: PlayerListAdapter
    private lateinit var playerListViewModel: PlayerListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.player_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val linearLayoutmg = LinearLayoutManager(activity?.applicationContext)
        val divider =
            DividerItemDecoration(playerListRecyclerView.context, DividerItemDecoration.VERTICAL)

        playerListAdapter = PlayerListAdapter()
        playerListViewModel = ViewModelProviders.of(this).get(PlayerListViewModel::class.java)
        playerListRecyclerView.adapter = playerListAdapter
        playerListRecyclerView.layoutManager = linearLayoutmg
        playerListRecyclerView.addItemDecoration(divider)

        listenPlayerList(playerListViewModel)
        observePlayerList(playerListViewModel)
        observeToast(playerListViewModel)
        observeTransition(playerListViewModel)
        setOnClickNextBtn(playerListViewModel)
    }

    fun listenPlayerList(viewModel: PlayerListViewModel) {
        playerListAdapter.setOnClickListener { pos, size ->
            if(pos == size - 1) viewModel.addPlayer()
        }
    }

    fun observePlayerList(viewModel: PlayerListViewModel) {
        viewModel.playerLiveData.observe(this, Observer {
            (activity as GameActivity).gameViewModel.setPlayers(it)
            playerListAdapter.setPlayers(it)
            playerListAdapter.notifyDataSetChanged()
        })
    }

    fun observeToast(viewModel: PlayerListViewModel) {
        viewModel.toastLiveData.observe(this, Observer {
            Toast.makeText(activity?.applicationContext, it, Toast.LENGTH_SHORT).show()
        })
    }

    fun observeTransition(viewModel: PlayerListViewModel) {
        viewModel.transitionLiveData.observe(this, Observer {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.gameFragmentLayout, SetNumberFragment.newInstance())?.commit()
        })
    }

    fun setOnClickNextBtn(viewModel: PlayerListViewModel) {
        nextBtn.setOnClickListener {
            viewModel.next()
        }
    }

}
