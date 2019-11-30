package net.mizucoffee.canislupus.activity

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.fragment.PlayerQrFragment
import net.mizucoffee.canislupus.viewmodel.AddPlayerViewModel


class AddPlayerActivity : AppCompatActivity() {

    lateinit var vm: AddPlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_player)

        vm = ViewModelProviders.of(this).get(AddPlayerViewModel::class.java)

        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.fragmentLayout, PlayerQrFragment.newInstance())
            transaction.commit()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        if(requestCode == 1)
            if (!(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED))
                finish()
    }
}
