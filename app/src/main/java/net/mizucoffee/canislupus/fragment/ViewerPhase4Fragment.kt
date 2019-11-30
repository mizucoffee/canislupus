package net.mizucoffee.canislupus.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_phase4.*
import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.model.viewer.Phase4

class ViewerPhase4Fragment(val data: String) : Fragment() {

    companion object {
        fun newInstance(data: String) = ViewerPhase4Fragment(data)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, s: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_phase4, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val p4 = Gson().fromJson<Phase4>(data, Phase4::class.java)
        today.text = p4.today
        punish.text = p4.punish
    }
}
