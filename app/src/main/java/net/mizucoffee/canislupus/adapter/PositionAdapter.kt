package net.mizucoffee.canislupus.adapter

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_position.view.*
import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.enumerate.PositionEnum
import net.mizucoffee.canislupus.werewolf.Position

class PositionAdapter(val playerCount: Int) :
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
        val position = Position.positionInit[PositionEnum.values()[p]]?.invoke()
        position?.apply {
            Position.defaultSet[playerCount]?.get(this.position)?.apply { countList[PositionEnum.values()[p]] = this }
            holder.symbol.setImageResource(symbol)
            holder.posName.text = name
            if (isRequired) {
                holder.btn0.isEnabled = false
                holder.btn0.setTextColor(Color.parseColor("#cccccc"))
            }
            countList[PositionEnum.values()[p]]?.also { setCount(holder, it) }

            holder.btn0.setOnClickListener {
                countList[PositionEnum.values()[p]] = 0
                setCount(holder, 0)
            }

            holder.btn1.setOnClickListener {
                countList[PositionEnum.values()[p]] = 1
                setCount(holder, 1)
            }

            holder.btn2.setOnClickListener {
                countList[PositionEnum.values()[p]] = 2
                setCount(holder, 2)
            }
        }

    }

    fun setCount(holder: PositionViewHolder, count: Int) {
        if (holder.btn0.isEnabled) {
            holder.btn0.setTextColor(primaryColor)
            holder.btn0.setBackgroundResource(R.drawable.button_number)
        }
        listOf(holder.btn1, holder.btn2).forEach {
            it.setTextColor(primaryColor)
            it.setBackgroundResource(R.drawable.button_number)
        }
        when (count) {
            0 -> holder.btn0
            1 -> holder.btn1
            else -> holder.btn2
        }.apply {
            setTextColor(Color.WHITE)
            setBackgroundResource(R.drawable.button_number_selected)
        }
    }

    class PositionViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val symbol: ImageView = v.symbol
        val posName: TextView = v.name
        val btn0: Button = v.btn0
        val btn1: Button = v.btn1
        val btn2: Button = v.btn2
    }
}