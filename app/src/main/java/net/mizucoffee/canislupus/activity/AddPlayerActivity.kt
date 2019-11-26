package net.mizucoffee.canislupus.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.fragment.QrFragment
import net.mizucoffee.canislupus.viewmodel.AddPlayerViewModel


class AddPlayerActivity : AppCompatActivity() {

    lateinit var vm: AddPlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_player)

        vm = ViewModelProviders.of(this).get(AddPlayerViewModel::class.java)

        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.fragmentLayout, QrFragment.newInstance())
            transaction.commit()
        }
    }
}
