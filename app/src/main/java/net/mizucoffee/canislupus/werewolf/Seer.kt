package net.mizucoffee.canislupus.werewolf

import android.content.Context
import android.view.View
import android.widget.TextView
import net.mizucoffee.canislupus.enumerate.PositionEnum

class Seer : Villager() {
    override val name: String = "占師"
    override val position: PositionEnum = PositionEnum.SEER
    override val description: String =
        "【特殊能力】\n自分以外の誰か1人、または余っているカード2枚を見る事ができます。\n\n【村人陣営の勝利条件】\n人狼を吊ることができれば勝利です。ただし、吊人を吊ってしまった場合はその時点で敗北となります。"

        override fun getMiniMessage(positions: List<Position>): String? = null

    override fun ability(positions: MutableList<Position>, selected: Int): List<Position> =
        positions

    override fun abilityResult(
        positions: MutableList<Position>,
        selectedKey: String,
        context: Context
    ): View? {
        val tv = TextView(context)
        val pos = positions.find { it.player?.id == selectedKey }
        pos?.also { p ->
            p.player?.also {
                tv.text = "${it.name}さんは${p.name}でした"
            }
        }
        return tv
    }

    override fun hasAbility(): Boolean = true
    override fun getSelectList(positions: MutableList<Position>): Map<String, String>? {
        val map = mutableMapOf<String, String>()
        positions
            .filter { it.player != null }
            .filter { it.player?.id != player?.id }
            .forEach { map["${it.player?.id}"] = it.name }
        map["OTHER"] = "場のカード"
        return map
    }

    override fun getSelectMessage(): String? = "占うカードを選んでください"
}