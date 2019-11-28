package net.mizucoffee.canislupus.repository

import android.content.Context
import net.mizucoffee.canislupus.model.Player

class PrefService {
    companion object {
        fun savePlayer(context: Context, player: Player) {
            context.getSharedPreferences("default", Context.MODE_PRIVATE).edit().apply{
                putString("id", player.id)
                putString("_id", player._id)
                putString("name", player.name)
                apply()
            }
        }
        fun loadPlayer(context: Context): Player? {
            val data = context.getSharedPreferences("default", Context.MODE_PRIVATE)
            val _id = data.getString("_id", "") ?: ""
            val id = data.getString("id", "") ?: ""
            val name = data.getString("name","") ?: ""
            if(_id == "" || id == "" || name == "") return null
            return Player(_id, id, name, 0, 0, 0)
        }
    }
}