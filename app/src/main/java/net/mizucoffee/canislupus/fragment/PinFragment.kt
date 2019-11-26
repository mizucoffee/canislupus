package net.mizucoffee.canislupus.fragment

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
import net.mizucoffee.canislupus.viewmodel.PinViewModel

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


    private fun observeTransition(viewModel: PinViewModel) {
        viewModel.pin.observe(this, Observer {
            if(it.length >= 4) {


                val intent = Intent()
                intent.putExtra("pin", it.toInt())
                activity?.setResult(RESULT_OK, intent)
                activity?.finish()
            }
        })
    }

}
