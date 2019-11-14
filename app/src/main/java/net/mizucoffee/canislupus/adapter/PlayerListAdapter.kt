package net.mizucoffee.canislupus.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.player_list_item.view.*
import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.model.Player

class PlayerListAdapter: RecyclerView.Adapter<PlayerListAdapter.PlayerViewHolder>() {

    private val list: ArrayList<Player> = ArrayList()
    private var listener: ((Int, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.player_list_item,parent,false)
        return PlayerViewHolder(view)
    }

    override fun getItemCount(): Int = list.size + 1

    override fun onBindViewHolder(holder: PlayerViewHolder, pos: Int) {
        val isPlayer = itemCount - 1 > pos

        holder.playerName.text = if (isPlayer) list[pos].name else "プレイヤーを追加"
        holder.playerName.setTextColor(Color.parseColor( if (isPlayer) "#000000" else "#CCCCCC"))
        holder.playerIcon.setImageResource(if (isPlayer) R.drawable.logo else R.drawable.ic_add_circle_black_24dp)
        holder.root.setOnClickListener {
            listener?.let { it1 -> it1(pos, itemCount) }
        }
    }

    fun setPlayers(players: ArrayList<Player>){
        list.clear()
        list.addAll(players)
        notifyDataSetChanged()
    }

    fun setOnClickListener(listener: (Int, Int) -> Unit) {
        this.listener = listener
    }

    class PlayerViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val playerName: TextView = view.playerName
        val playerIcon: ImageView = view.playerIcon
        val root: View = view.rootView
    }
}