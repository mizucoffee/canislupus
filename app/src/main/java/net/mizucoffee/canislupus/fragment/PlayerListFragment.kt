package net.mizucoffee.canislupus.fragment

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_player_list.*
import net.mizucoffee.canislupus.activity.GameActivity

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.activity.AddPlayerActivity
import net.mizucoffee.canislupus.databinding.FragmentPlayerListBinding
import net.mizucoffee.canislupus.model.Player
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
        binding.lifecycleOwner = this
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
            when(it) {
                0 -> {
                    val transaction = activity?.supportFragmentManager?.beginTransaction()
                    transaction?.replace(R.id.gameFragmentLayout, SetNumberFragment.newInstance())?.commit()
                }
                1 -> {
                    startActivityForResult(Intent(activity, AddPlayerActivity::class.java), 0)
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            0 -> if (AppCompatActivity.RESULT_OK == resultCode) {
                val player = data?.getSerializableExtra("player")
                if(player is Player)
                    binding.viewModel?.setPlayer(player)
                else
                    binding.viewModel?.setGuest()
            } else {
                Toast.makeText(activity, "ログインに失敗しました", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
