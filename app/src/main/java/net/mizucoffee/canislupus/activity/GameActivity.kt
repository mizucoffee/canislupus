package net.mizucoffee.canislupus.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.fragment.PlayerListFragment
import net.mizucoffee.canislupus.model.ApiResponse
import net.mizucoffee.canislupus.model.GameInit
import net.mizucoffee.canislupus.repository.CanislupusService
import net.mizucoffee.canislupus.repository.simpleCall
import net.mizucoffee.canislupus.viewmodel.GameViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder


class GameActivity : AppCompatActivity() {
    lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        gameViewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        CanislupusService.createService().initGame()
            .enqueue(object : Callback<ApiResponse<GameInit>> {
                override fun onFailure(call: Call<ApiResponse<GameInit>>, t: Throwable) {}

                override fun onResponse(
                    call: Call<ApiResponse<GameInit>>,
                    response: Response<ApiResponse<GameInit>>
                ) {
                    gameViewModel.gameId.postValue(response.body()?.data?.id)
                    CanislupusService.createService()
                        .postGame("${response.body()?.data?.id}", 0, "{}")
                        .simpleCall()
                }
            })

        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.gameFragmentLayout, PlayerListFragment.newInstance())
            transaction.commit()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder(this).apply {
                setTitle("確認")
                setMessage("ゲームを終了しても良いですか？")
                setPositiveButton("Ok") { _, _ -> finish() }
                setNegativeButton("Cancel", null)
                show()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_game, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_qr) {
            if(gameViewModel.gameId.value != null){

                val barcodeEncoder = BarcodeEncoder()
                val bitmap = barcodeEncoder.encodeBitmap(
                    gameViewModel.gameId.value,
                    BarcodeFormat.QR_CODE,
                    400,
                    400
                )
                val dialog = AlertDialog.Builder(this)
                    .setTitle("タブレット連携")
                    .setView(R.layout.alert_qr_code)
                    .setPositiveButton("OK", null)
                    .setCancelable(true)
                    .create()
                dialog.show()
                dialog.findViewById<ImageView>(R.id.qrcode)?.setImageBitmap(bitmap)
            } else {
                AlertDialog.Builder(this)
                    .setTitle("タブレット連携")
                    .setMessage("ゲームIDがありません。インターネット接続を確認してください。")
                    .setPositiveButton("OK", null)
                    .setCancelable(true)
                    .show()
            }
        }
        return true
    }
}
