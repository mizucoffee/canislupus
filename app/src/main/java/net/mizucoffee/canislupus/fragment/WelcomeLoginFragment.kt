package net.mizucoffee.canislupus.fragment

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import net.mizucoffee.canislupus.activity.LoginActivity
import net.mizucoffee.canislupus.databinding.FragmentWelcomeLoginBinding
import net.mizucoffee.canislupus.repository.PrefService
import net.mizucoffee.canislupus.viewmodel.WelcomeLoginViewModel

class WelcomeLoginFragment : Fragment() {

    companion object {
        fun newInstance() = WelcomeLoginFragment()
    }

    private lateinit var binding: FragmentWelcomeLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        binding = FragmentWelcomeLoginBinding.inflate(inflater, container, false)
        binding.viewModel = ViewModelProviders.of(this).get(WelcomeLoginViewModel::class.java)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel?.also {
            observeToast(it)
            observeLogin(it)
            observeTransition(it)
        }
    }

    private fun observeToast(viewModel: WelcomeLoginViewModel) {
        viewModel.toast.observe(this, Observer {
            if (it != null) Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun observeLogin(viewModel: WelcomeLoginViewModel) {
        viewModel.login.observe(this, Observer {
            startActivityForResult(Intent(activity, LoginActivity::class.java), 1)
        })
    }

    private fun observeTransition(viewModel: WelcomeLoginViewModel) {
        viewModel.transition.observe(this, Observer {
            activity?.applicationContext?.let { context -> PrefService.savePlayer(context, it) }
            activity?.setResult(RESULT_OK)
            activity?.finish()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) activity?.finish()
    }

    override fun onPause() {
        super.onPause()
        binding.viewModel?.toast?.value = null
    }
}