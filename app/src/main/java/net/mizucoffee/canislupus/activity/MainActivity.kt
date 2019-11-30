package net.mizucoffee.canislupus.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.AppLaunchChecker
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.stephentuso.welcome.WelcomeHelper
import kotlinx.android.synthetic.main.activity_main.*
import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.repository.PrefService


class MainActivity : AppCompatActivity() {

    lateinit var welcomeScreen: WelcomeHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (PrefService.loadPlayer(this) == null) {
            welcomeScreen = WelcomeHelper(this, WelcomeScreenActivity::class.java)
            welcomeScreen.forceShow()
        }

        gameStartBtn.setOnClickListener {
            startActivity(Intent(applicationContext, GameActivity::class.java))
        }
        viewModeBtn.setOnClickListener {
            startActivity(Intent(applicationContext, ViewerActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_license) {
            val intent = Intent(this, OssLicensesMenuActivity::class.java)
            intent.putExtra("title", "Open Source License")
            startActivity(intent)
        }
        return true
    }
}
