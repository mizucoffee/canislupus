package net.mizucoffee.canislupus.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_card.view.*
import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.enumerate.CardEnum

class PositionAdapter(private val playerCount: Int) :
    RecyclerView.Adapter<PositionAdapter.PositionViewHolder>() {

    val countList: MutableMap<CardEnum, Int> = mutableMapOf()
    var primaryColor: Int = 0
    var listener: ((count: Int) -> Unit)? = null

    override fun onCreateViewHolder(p: ViewGroup, viewType: Int): PositionViewHolder {
        val view = LayoutInflater.from(p.context).inflate(R.layout.item_card, p, false)
        primaryColor = ContextCompat.getColor(p.context, R.color.colorPrimary)
        return PositionViewHolder(view)
    }

    override fun getItemCount(): Int = CardEnum.values().size

    override fun onBindViewHolder(holder: PositionViewHolder, p: Int) {
        val card = CardEnum.values()[p].init()
        val defaultPlayers = card.defaultPlayers[playerCount] ?: 0
        countList[card.card] = defaultPlayers

        holder.symbol.setImageResource(card.symbol)
        holder.posName.text = card.name
        if (card.isRequired) {
            holder.btn[0].isEnabled = false
            holder.btn[0].setTextColor(Color.parseColor("#cccccc"))
        }
        setCount(holder, defaultPlayers)

        holder.btn.forEachIndexed { i, btn ->
            btn.setOnClickListener {
                countList[CardEnum.values()[p]] = i
                listener?.invoke(countList.toList().sumBy { it.second })
                setCount(holder, i)
            }
        }
    }

    fun setCountListener(listener: (count: Int) -> Unit) {
        this.listener = listener
    }

    private fun setCount(holder: PositionViewHolder, c: Int) {
        holder.btn.filter { it.isEnabled }.forEach {
            it.setTextColor(primaryColor)
            it.setBackgroundResource(R.drawable.button_number)
        }
        if (c > 2) return
        holder.btn[c].setTextColor(Color.WHITE)
        holder.btn[c].setBackgroundResource(R.drawable.button_number_selected)
    }

    class PositionViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val symbol: ImageView = v.symbol
        val posName: TextView = v.name
        val btn: List<Button> = listOf(v.btn0, v.btn1, v.btn2)
    }
}

@BindingAdapter("playerListAdapter")
fun RecyclerView.setAdapter(adapter: PositionAdapter) {
    this.adapter = adapter
}
