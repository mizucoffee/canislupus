package net.mizucoffee.canislupus.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.common.api.Api
import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.fragment.PlayerListFragment
import net.mizucoffee.canislupus.model.ApiResponse
import net.mizucoffee.canislupus.model.Game
import net.mizucoffee.canislupus.model.GameInit
import net.mizucoffee.canislupus.repository.CanislupusService
import net.mizucoffee.canislupus.viewmodel.GameViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GameActivity : AppCompatActivity() {
    lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

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
                        .enqueue(object : Callback<ApiResponse<Game>> {
                            override fun onFailure(call: Call<ApiResponse<Game>>, t: Throwable) {
                            }

                            override fun onResponse(
                                c: Call<ApiResponse<Game>>,
                                r: Response<ApiResponse<Game>>
                            ) {
                            }
                        })
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
}
