package net.mizucoffee.canislupus.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_player_list.view.*
import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.model.Player

class PlayerListAdapter : RecyclerView.Adapter<PlayerListAdapter.PlayerViewHolder>() {

    private val list: MutableList<Player> = mutableListOf()
    var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_player_list, parent, false)
        return PlayerViewHolder(view)
    }

    override fun getItemCount(): Int = if (list.size < 6) list.size + 1 else list.size

    override fun onBindViewHolder(holder: PlayerViewHolder, pos: Int) {
        val isPlayer = list.size > pos
        holder.apply {
            playerName.text = if (isPlayer) list[pos].name else "プレイヤーを追加"
            playerName.setTextColor(Color.parseColor(if (isPlayer) "#000000" else "#CCCCCC"))
            playerIcon.setImageResource(if (isPlayer) R.drawable.logo else R.drawable.ic_add_circle_black_24dp)
            root.setOnClickListener { listener?.let { it1 -> it1.onItemCLickListener(pos, itemCount) } }
        }
    }

    var players: MutableList<Player>
        get() = list
        set(players) {
            list.clear()
            list.addAll(players)
            notifyDataSetChanged()
        }

    class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val playerName: TextView = view.playerName
        val playerIcon: ImageView = view.playerIcon
        val root: View = view.rootView
    }

    interface OnItemClickListener {
        fun onItemCLickListener(pos: Int, count: Int)
    }
}

@BindingAdapter("playerListAdapter")
fun RecyclerView.setAdapter(adapter: PlayerListAdapter) {
    this.adapter = adapter
}

@BindingAdapter("onPlayerListClick")
fun RecyclerView.setOnItemClickListener(listener: PlayerListAdapter.OnItemClickListener) {
    (this.adapter as PlayerListAdapter).listener = listener
}