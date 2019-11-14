package net.mizucoffee.canislupus.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_list_item.view.*
import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.model.User

class UserListAdapter: RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    private val list: ArrayList<User> = ArrayList()
    private var listener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list_item,parent,false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int = list.size + 1

    override fun onBindViewHolder(holder: UserViewHolder, pos: Int) {
        val isUser = itemCount - 1 > pos

        holder.userName.text = if (isUser) list[pos].name else "ユーザーを追加"
        holder.userName.setTextColor(Color.parseColor( if (isUser) "#000000" else "#CCCCCC"))
        holder.userIcon.setImageResource(if (isUser) R.drawable.logo else R.drawable.ic_add_circle_black_24dp)
        holder.root.setOnClickListener {
            listener?.let { it1 -> it1(pos) }
        }
    }

    fun addUsers(users: ArrayList<User>){
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    fun setOnClickListener(listener: (Int) -> Unit) {
        this.listener = listener
    }

    class UserViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val userName: TextView = view.nameTextView
        val userIcon: ImageView = view.userIcon
        val root: View = view.rootView
    }
}