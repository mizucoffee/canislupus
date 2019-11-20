package net.mizucoffee.canislupus.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_result.*

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.activity.GameActivity
import net.mizucoffee.canislupus.enumerate.PositionEnum
import net.mizucoffee.canislupus.viewmodel.ResultViewModel
import net.mizucoffee.canislupus.werewolf.Position

class ResultFragment : Fragment() {

    companion object {
        fun newInstance() = ResultFragment()
    }

    private lateinit var viewModel: ResultViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    private fun getGVM() = (activity as GameActivity).gameViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ResultViewModel::class.java)

        result.text =
            "${Position.checkWinner(getGVM().getExecuteList(), getGVM().getPositionList()).result}"
        sub.text =
            "${Position.checkWinner(getGVM().getExecuteList(), getGVM().getPositionList()).sub}"

        listenNextBtn(viewModel)
        observeTransition(viewModel)
    }

    fun listenNextBtn(viewModel: ResultViewModel) {
        newGameBtn.setOnClickListener {
            viewModel.next()
        }
    }

    fun observeTransition(viewModel: ResultViewModel) {
        viewModel.transition.observe(this, Observer {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.gameFragmentLayout, PlayerListFragment.newInstance())
                ?.commit()
        })
    }
}
