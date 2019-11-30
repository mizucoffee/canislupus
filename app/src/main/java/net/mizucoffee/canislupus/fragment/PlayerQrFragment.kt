package net.mizucoffee.canislupus.fragment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.activity.AddPlayerActivity
import net.mizucoffee.canislupus.viewmodel.PlayerQrViewModel
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import kotlinx.android.synthetic.main.fragment_player_qr.*
import net.mizucoffee.canislupus.databinding.FragmentPlayerQrBinding
import io.socket.emitter.Emitter
import io.socket.client.IO
import io.socket.client.Socket


class PlayerQrFragment : Fragment() {

    companion object {
        fun newInstance() = PlayerQrFragment()
    }

    private fun getAVM() = (activity as AddPlayerActivity).vm
    private lateinit var binding: FragmentPlayerQrBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        binding = FragmentPlayerQrBinding.inflate(inflater, container, false)
        binding.viewModel = ViewModelProviders.of(this).get(PlayerQrViewModel::class.java)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.let {
            ActivityCompat.requestPermissions(
                it,
                arrayOf(Manifest.permission.CAMERA),
                1
            )
        }

        activity?.title = "canislupus - ログイン"
        barcode_view.decodeSingle(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                if (result?.text == null) return

                getAVM().qr = result.text
                val transaction = activity?.supportFragmentManager?.beginTransaction()
                transaction?.replace(R.id.fragmentLayout, PinFragment.newInstance())
                    ?.commit()
            }

            override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {
            }
        })

        binding.viewModel?.also { observeAlert(it) }
    }

    private fun observeAlert(viewModelPlayer: PlayerQrViewModel) {
        viewModelPlayer.alert.observe(this, Observer {
            activity?.let {
                AlertDialog.Builder(it)
                    .setTitle("QRコードを作成する")
                    .setView(R.layout.alert_new_account)
                    .setPositiveButton("OK", null)
                    .setNegativeButton(
                        "GUEST MODE"
                    ) { _, _ ->
                        val intent = Intent()
                        activity?.setResult(RESULT_OK, intent)
                        activity?.finish()
                    }
                    .setCancelable(true)
                    .show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        barcode_view.resume()
    }

    override fun onPause() {
        super.onPause()
        barcode_view.pause()
    }
}
