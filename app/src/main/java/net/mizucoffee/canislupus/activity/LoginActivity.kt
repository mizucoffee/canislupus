package net.mizucoffee.canislupus.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.databinding.ActivityLoginBinding
import net.mizucoffee.canislupus.repository.PrefService
import net.mizucoffee.canislupus.viewmodel.LoginViewModel
import net.mizucoffee.canislupus.viewmodel.WelcomeLoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding.lifecycleOwner = this

        binding.viewModel?.also {
            observeToast(it)
            observeTransition(it)
        }
    }

    private fun observeToast(viewModel: LoginViewModel) {
        viewModel.toast.observe(this, Observer {
            if (it != null) Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
        })
    }


    private fun observeTransition(viewModel: LoginViewModel) {
        viewModel.transition.observe(this, Observer {
            PrefService.savePlayer(applicationContext, it)
            setResult(RESULT_OK)
            finish()
        })
    }
}
