package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.mizucoffee.canislupus.model.ApiResponse
import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.model.Qr
import net.mizucoffee.canislupus.repository.CanislupusService
import net.mizucoffee.canislupus.repository.Crypt
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    val transition = MutableLiveData<Player>()
    val id = MutableLiveData<String>("")
    val passcode = MutableLiveData<String>("")
    val toast = MutableLiveData<String>()

    fun login() {

        if (id.value == "" || passcode.value == "") {
            toast.postValue("全ての項目を入力してください")
            return
        }
        if (passcode.value?.length != 4) {
            toast.postValue("パスコードは4桁入力してください")
            return
        }

        CanislupusService.createService().authPin("${id.value}").enqueue(
            object : Callback<ApiResponse<Qr>> {
                override fun onFailure(call: Call<ApiResponse<Qr>>, t: Throwable) {
                    toast.postValue("インターネット接続を確認してください")
                }

                override fun onResponse(
                    call: Call<ApiResponse<Qr>>,
                    res: Response<ApiResponse<Qr>>
                ) {
                    res.body()?.let {

                        CanislupusService.createService().playerAuth(
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
                                    toast.postValue("ログインに失敗しました")
                                }
                            }
                        })

                    }
                }
            }
        )
    }
}
