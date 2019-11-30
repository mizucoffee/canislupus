package net.mizucoffee.canislupus.fragment

import android.Manifest
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.activity.AddPlayerActivity
import net.mizucoffee.canislupus.viewmodel.PlayerQrViewModel
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import kotlinx.android.synthetic.main.fragment_player_qr.*
import net.mizucoffee.canislupus.activity.ViewerActivity
import net.mizucoffee.canislupus.databinding.FragmentViewerQrBinding
import net.mizucoffee.canislupus.viewmodel.ViewerQrViewModel


class ViewerQrFragment : Fragment() {

    companion object {
        fun newInstance() = ViewerQrFragment()
    }

    private fun getVVM() = (activity as ViewerActivity).vm
    private lateinit var binding: FragmentViewerQrBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        binding = FragmentViewerQrBinding.inflate(inflater, container, false)
        binding.viewModel = ViewModelProviders.of(this).get(ViewerQrViewModel::class.java)
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

        activity?.title = "canislupus - Viewer認証"
        barcode_view.decodeSingle(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                if (result?.text == null) return

                getVVM().gameId = result.text
                (activity as ViewerActivity).listenSocketIo()
            }

            override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {
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
