package net.mizucoffee.canislupus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_phase1.*
import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.model.viewer.Phase1

class ViewerPhase1Fragment(val data: String) : Fragment() {

    companion object {
        fun newInstance(data: String) = ViewerPhase1Fragment(data)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_phase1, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val p1 = Gson().fromJson<Phase1>(data, Phase1::class.java)
        player.text = "${p1.name}さん"
    }
}
