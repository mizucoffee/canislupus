package net.mizucoffee.canislupus.fragment

import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.DialogInterface
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer

import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.activity.AddPlayerActivity
import net.mizucoffee.canislupus.databinding.FragmentQrBinding
import net.mizucoffee.canislupus.viewmodel.QrViewModel
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import kotlinx.android.synthetic.main.fragment_qr.*


class QrFragment : Fragment() {

    companion object {
        fun newInstance() = QrFragment()
    }

    private fun getAVM() = (activity as AddPlayerActivity).vm
    private lateinit var binding: FragmentQrBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        binding = FragmentQrBinding.inflate(inflater, container, false)
        binding.viewModel = ViewModelProviders.of(this).get(QrViewModel::class.java)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.title = "canislupus - ログイン"
        barcode_view.decodeSingle(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                if(result?.text == null) return

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


    private fun observeAlert(viewModel: QrViewModel) {
        viewModel.alert.observe(this, Observer {
            activity?.let {
                AlertDialog.Builder(it)
                    .setTitle("QRコードを作成する")
                    .setView(R.layout.alert_new_account)
                    .setPositiveButton("OK", null)
                    .setNegativeButton("GUEST MODE"
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
