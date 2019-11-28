package net.mizucoffee.canislupus.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_show_card.*
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
            observeTransition(it)
            observeSnackbar(it)
        }
    }

    private var snackbar: Snackbar? = null
    private fun observeSnackbar(viewModel: LoginViewModel) {
        viewModel.snackbar.observe(this, Observer {
            snackbar?.dismiss()
            val msg = when (it) {
                1 -> "ログイン中..."
                2 -> "インターネット接続を確認してください"
                3 -> "ログインに失敗しました"
                4 -> "全ての項目を入力してください"
                5 -> "パスコードは4桁入力してください"
                else -> null
            }
            if (msg != null) {
                snackbar = Snackbar.make(binding.root, msg, Snackbar.LENGTH_INDEFINITE)
                snackbar?.show()
            }
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
