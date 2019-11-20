package net.mizucoffee.canislupus.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import net.mizucoffee.canislupus.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameStartBtn.setOnClickListener {
            startActivity(Intent(applicationContext, GameActivity::class.java))
        }
    }
}
