package net.mizucoffee.canislupus.fragment

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import net.mizucoffee.canislupus.activity.AddPlayerActivity
import net.mizucoffee.canislupus.databinding.FragmentPinBinding
import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.model.ApiResponse
import net.mizucoffee.canislupus.repository.CanislupusService
import net.mizucoffee.canislupus.viewmodel.PinViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PinFragment : Fragment() {

    companion object {
        fun newInstance() = PinFragment()
    }

    private fun getAVM() = (activity as AddPlayerActivity).vm
    private lateinit var binding: FragmentPinBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        binding = FragmentPinBinding.inflate(inflater, container, false)
        binding.viewModel = ViewModelProviders.of(this).get(PinViewModel::class.java)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel?.also { observeTransition(it) }
    }

    private val cipher = "cpm8sk10hxgwy95uen7ofr4lvbj32tadq6zi"
    private val plain = "0123456789abcdefghijklmnopqrstuvwxyz"

    fun crypt(text: String, number: Int): String {
        var result = text
        var crypted = ""
        for (k in 0..number) {
            for (i in result.indices) {
                crypted += cipher[(plain.indexOf(result[i]) + i) % plain.length]
            }
            result = crypted
            crypted = ""
        }
        return result
    }

    private fun observeTransition(viewModel: PinViewModel) {
        viewModel.pin.observe(this, Observer {
            if(it.length >= 4) {
                CanislupusService.createService().playerAuth(getAVM().qr, crypt(getAVM().qr, it.toInt())).enqueue(
                    object : Callback<ApiResponse<Player>> {
                        override fun onFailure(call: Call<ApiResponse<Player>>, t: Throwable) {
                            val intent = Intent()
                            activity?.setResult(RESULT_CANCELED, intent)
                            activity?.finish()
                        }

                        override fun onResponse(
                            call: Call<ApiResponse<Player>>,
                            response: Response<ApiResponse<Player>>
                        ) {
                            val intent = Intent()

                            if(response.body()?.data != null) {
                                intent.putExtra("player", response.body()?.data)
                                activity?.setResult(RESULT_OK, intent)
                            } else {
                                activity?.setResult(RESULT_CANCELED, intent)
                            }
                            activity?.finish()
                        }
                    }
                )

            }
        })
    }

}
