package net.mizucoffee.canislupus.werewolf

import android.content.Context
import android.view.View
import android.widget.TextView
import net.mizucoffee.canislupus.enumerate.PositionEnum

class Thief : Villager() {

    override val name: String = "怪盗"
    override val position: PositionEnum = PositionEnum.THIEF
    override val description: String =
        "【特殊能力】\n自分以外の誰か1人を選び、自分とその人の役職を入れ替えます。人狼陣営と入れ替わった場合、お互いの勝利条件も入れ替わります。\n\n【村人陣営の勝利条件】\n人狼を吊ることができれば勝利です。ただし、吊人を吊ってしまった場合はその時点で敗北となります。\n\n人狼が吊られなければ勝利です。ただし、吊人が吊られた　場合は吊人の単独勝利となります。"

    override fun getMiniMessage(positions: List<Position>): String? = null

    override fun ability(positions: MutableList<Position>, selected: Int): List<Position> {
        positions.map {
            if (it.player?.id == player?.id) it.player = positions[selected].player
            it
        }
        positions[selected].player = player
        return positions
    }

    override fun abilityResult(
        positions: MutableList<Position>,
        selectedKey: String,
        context: Context
    ): View? {
        val tv = TextView(context)
        tv.text = "あなたは${positions.find { it.player?.id == selectedKey }?.name}になりました"
        return tv
    }

    override fun hasAbility(): Boolean = true
    override fun getSelectList(positions: MutableList<Position>): Map<String, String>? {
        val map = mutableMapOf<String, String>()
        positions
            .filter { it.player != null }
            .filter { it.player?.id != player?.id }
            .forEach { map["${it.player?.id}"] = it.name }
        return map
    }

    override fun getSelectMessage(): String? = "入れ替わる人を選択してください"
}