package net.mizucoffee.canislupus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AlertDialog
import net.mizucoffee.canislupus.fragment.UserListFragment

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.gameFragmentLayout, UserListFragment.newInstance())
            transaction.commit()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder(this).let {
                it.setTitle("確認")
                it.setMessage("ゲームを終了しても良いですか？")
                it.setPositiveButton("Ok") { _, _ -> finish() }
                it.setNegativeButton("Cancel", null)
                it.show()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
