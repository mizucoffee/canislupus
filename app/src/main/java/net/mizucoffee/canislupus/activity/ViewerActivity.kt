package net.mizucoffee.canislupus.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.ViewModelProviders
import io.socket.client.IO
import io.socket.client.Socket
import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.fragment.*
import net.mizucoffee.canislupus.viewmodel.ViewerViewModel

class ViewerActivity : AppCompatActivity() {
    lateinit var vm: ViewerViewModel
    private lateinit var socket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        vm = ViewModelProviders.of(this).get(ViewerViewModel::class.java)

        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.gameFragmentLayout, ViewerQrFragment.newInstance())
            transaction.commit()
        }
    }

    fun listenSocketIo() {
        socket = IO.socket("http://canislupus.mizucoffee.com")
        socket.apply {
            on(Socket.EVENT_CONNECT) {
                socket.emit("join", vm.gameId)
            }
            on("game") { (phase, data) ->
                supportFragmentManager.beginTransaction().apply {
                    val f = when ("$phase") {
                        "0" -> ViewerPhase0Fragment.newInstance("$data")
                        "1" -> ViewerPhase1Fragment.newInstance("$data")
                        "2" -> ViewerPhase2Fragment.newInstance("$data")
                        "3" -> ViewerPhase3Fragment.newInstance("$data")
                        "4" -> ViewerPhase4Fragment.newInstance("$data")
                        "5" -> ViewerPhase5Fragment.newInstance("$data")
                        else -> ViewerPhase1Fragment.newInstance("$data")
                    }
                    replace(R.id.gameFragmentLayout, f)
                    commit()
                }
            }
            connect()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::socket.isInitialized) socket.disconnect()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        val decorView = window.decorView
        decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                )
    }
}