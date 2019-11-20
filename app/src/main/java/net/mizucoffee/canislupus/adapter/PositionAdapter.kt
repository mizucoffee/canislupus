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
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_position.view.*
import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.enumerate.PositionEnum
import net.mizucoffee.canislupus.werewolf.Position

class PositionAdapter(private val playerCount: Int) :
    RecyclerView.Adapter<PositionAdapter.PositionViewHolder>() {

    val countList: MutableMap<PositionEnum, Int> = mutableMapOf()
    var primaryColor: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_position, parent, false)
        primaryColor = ContextCompat.getColor(parent.context, R.color.colorPrimary)
        return PositionViewHolder(view)
    }

    override fun getItemCount(): Int = PositionEnum.values().size

    override fun onBindViewHolder(holder: PositionViewHolder, p: Int) {
        Position.positionInit[PositionEnum.values()[p]]?.invoke()?.apply {
            Position.defaultSet[playerCount]?.get(this.position)
                ?.apply { countList[PositionEnum.values()[p]] = this }

            holder.symbol.setImageResource(symbol)
            holder.posName.text = name
            if (isRequired) {
                holder.btn[0].isEnabled = false
                holder.btn[0].setTextColor(Color.parseColor("#cccccc"))
            }
            countList[PositionEnum.values()[p]]?.also { setCount(holder, it) }

            holder.btn.forEachIndexed { i, btn ->
                btn.setOnClickListener {
                    countList[PositionEnum.values()[p]] = i
                    setCount(holder, i)
                }
            }
        }
    }

    private fun setCount(holder: PositionViewHolder, c: Int) {
        holder.btn.filter { it.isEnabled }.forEach {
            it.setTextColor(primaryColor)
            it.setBackgroundResource(R.drawable.button_number)
        }
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
