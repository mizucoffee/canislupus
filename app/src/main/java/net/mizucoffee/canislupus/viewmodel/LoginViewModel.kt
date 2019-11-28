package net.mizucoffee.canislupus.viewmodel

import android.view.View
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
    val snackbar = MutableLiveData<Int>()

    fun login(v: View) {

        if (id.value == "" || passcode.value == "") {
            snackbar.postValue(4)
            return
        }
        if (passcode.value?.length != 4) {
            snackbar.postValue(5)
            return
        }

        v.isEnabled = false
        snackbar.postValue(1)

        CanislupusService.createService().authPin("${id.value}").enqueue(
            object : Callback<ApiResponse<Qr>> {
                override fun onFailure(call: Call<ApiResponse<Qr>>, t: Throwable) {
                    snackbar.postValue(2)
                    v.isEnabled = true
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
                                snackbar.postValue(3)
                                v.isEnabled = true
                            }

                            override fun onResponse(
                                call: Call<ApiResponse<Player>>,
                                res: Response<ApiResponse<Player>>
                            ) {
                                if (res.isSuccessful) {
                                    snackbar.postValue(0)
                                    transition.postValue(res.body()?.data)
                                } else {
                                    snackbar.postValue(3)
                                }
                                v.isEnabled = true
                            }
                        })
                    }
                }
            }
        )
    }
}
