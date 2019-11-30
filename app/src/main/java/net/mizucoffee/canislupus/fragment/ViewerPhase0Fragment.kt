package net.mizucoffee.canislupus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import net.mizucoffee.canislupus.R

class ViewerPhase0Fragment(val data: String) : Fragment() {

    companion object {
        fun newInstance(data: String) = ViewerPhase0Fragment(data)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_phase0, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}
