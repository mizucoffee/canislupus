package net.mizucoffee.canislupus.viewmodel

import android.transition.Transition
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.runBlocking
import net.mizucoffee.canislupus.model.ApiResponse
import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.model.Qr
import net.mizucoffee.canislupus.repository.CanislupusService
import net.mizucoffee.canislupus.repository.Crypt
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class WelcomeLoginViewModel : ViewModel() {
    val id = MutableLiveData<String>("")
    val name = MutableLiveData<String>("")
    val passcode = MutableLiveData<String>("")
    val toast = MutableLiveData<String>()
    val transition = MutableLiveData<Player>()
    val login = MutableLiveData<Int>()

    fun register() {
        if (id.value == "" || name.value == "" || passcode.value == "") {
            toast.postValue("全ての項目を入力してください")
            return
        }
        if (passcode.value?.length != 4) {
            toast.postValue("パスコードは4桁入力してください")
            return
        }

        CanislupusService.createService().uniq().enqueue(
            object : Callback<ApiResponse<Qr>> {
                override fun onFailure(call: Call<ApiResponse<Qr>>, t: Throwable) {
                    toast.postValue("インターネット接続を確認してください")
                }

                override fun onResponse(
                    call: Call<ApiResponse<Qr>>,
                    res: Response<ApiResponse<Qr>>
                ) {
                    res.body()?.let {

                        CanislupusService.createService().player(
                            "${id.value}",
                            "${name.value}",
                            it.data.qr,
                            Crypt.crypt(it.data.qr, passcode.value?.toInt() ?: 0)
                        ).enqueue(object : Callback<ApiResponse<Player>> {
                            override fun onFailure(call: Call<ApiResponse<Player>>, t: Throwable) {
                                toast.postValue("失敗しました")
                            }

                            override fun onResponse(
                                call: Call<ApiResponse<Player>>,
                                res: Response<ApiResponse<Player>>
                            ) {
                                if(res.isSuccessful) {
                                    transition.postValue(res.body()?.data)
                                } else {
                                    if(res.code() == 400)
                                        toast.postValue("既に登録されているIDです\n別のIDをお試しください")
                                    else
                                        toast.postValue("失敗しました")
                                }
                            }
                        })

                    }
                }
            }
        )
    }

    fun login() {
        login.postValue(0)
    }

}
