package net.mizucoffee.canislupus.werewolf

import android.content.Context
import android.view.View
import android.widget.TextView
import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.enumerate.CardEnum

class Seer : Villager() {
    override val name: String = "占師"
    override val card: CardEnum = CardEnum.SEER
    override val description: String =
        "【特殊能力】\n自分以外の誰か1人、または余っているカード2枚を見る事ができます。\n\n【村人陣営の勝利条件】\n人狼を吊ることができれば勝利です。ただし、吊人を吊ってしまった場合はその時点で敗北となります。"
    override val symbol: Int = R.drawable.seer
    override val defaultPlayers: Map<Int, Int> = mapOf(3 to 1, 4 to 1, 5 to 1, 6 to 1)
    override val isRequired: Boolean = false

    override fun getMiniMessage(cards: List<Card>): String? = null
    override fun ability(cards: MutableList<Card>, selectedKey: String): MutableList<Card> = cards
    override fun abilityResult(cards: MutableList<Card>, key: String, context: Context): View? {
        val tv = TextView(context)
        val pos = cards.find { it.owner?.id == key }
        pos?.also { p ->
            p.owner?.also {
                tv.text = "${it.name}さんは${p.name}でした"
            }
        }
        if (pos == null) {
            val list = cards.filter { it.owner == null }.map { it.name }
            tv.text = "場のカードは${list.joinToString("と")}でした"
        }
        return tv
    }
    override fun hasAbility(): Boolean = true
    override fun shouldSelectList(): Boolean = true
    override fun getSelectList(cards: MutableList<Card>): Map<String, String>? {
        val map = mutableMapOf<String, String>()
        cards
            .filter { it.owner != null }
            .filter { it.owner?.id != owner?.id }
            .forEach { map["${it.owner?.id}"] = "${it.owner?.name}" }
        map["OTHER"] = "場のカード"
        return map
    }
    override fun getSelectMessage(): String? = "占うカードを選んでください"
}