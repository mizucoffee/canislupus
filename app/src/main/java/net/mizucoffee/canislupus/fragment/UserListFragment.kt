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
import kotlinx.android.synthetic.main.user_list_fragment.*

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.adapter.UserListAdapter
import net.mizucoffee.canislupus.viewmodel.UserListViewModel

class UserListFragment : Fragment() {

    companion object {
        fun newInstance() = UserListFragment()
    }

    private lateinit var userListAdapter: UserListAdapter
    private lateinit var userListViewModel: UserListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val linearLayoutmg = LinearLayoutManager(activity?.applicationContext)
        val divider =
            DividerItemDecoration(userListRecyclerView.context, DividerItemDecoration.VERTICAL)

        userListAdapter = UserListAdapter()
        userListViewModel = ViewModelProviders.of(this).get(UserListViewModel::class.java)
        userListRecyclerView.adapter = userListAdapter
        userListRecyclerView.layoutManager = linearLayoutmg
        userListRecyclerView.addItemDecoration(divider)

        listenUserList(userListViewModel)
        observeUserList(userListViewModel)
        observeToast(userListViewModel)
        observeTransition(userListViewModel)
        setOnClickNextBtn(userListViewModel)
    }

    fun listenUserList(viewModel: UserListViewModel) {
        userListAdapter.setOnClickListener { pos, size ->
            if(pos == size - 1) viewModel.addUser()
        }
    }

    fun observeUserList(viewModel: UserListViewModel) {
        viewModel.userLiveData.observe(this, Observer {
            userListAdapter.addUsers(it)
            userListAdapter.notifyDataSetChanged()
        })
    }

    fun observeToast(viewModel: UserListViewModel) {
        viewModel.toastLiveData.observe(this, Observer {
            Toast.makeText(activity?.applicationContext, it, Toast.LENGTH_SHORT).show()
        })
    }

    fun observeTransition(viewModel: UserListViewModel) {
        viewModel.transitionLiveData.observe(this, Observer {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.gameFragmentLayout, SetNumberFragment.newInstance())?.commit()
        })
    }

    fun setOnClickNextBtn(viewModel: UserListViewModel) {
        nextBtn.setOnClickListener {
            viewModel.next()
        }
    }

}
