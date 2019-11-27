package net.mizucoffee.canislupus.model.werewolf

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.enumerate.CardEnum

class Thief : Villager() {

    override val name: String = "怪盗"
    override val card: CardEnum = CardEnum.THIEF
    override val description: String =
        "【特殊能力】\n自分以外の誰か1人を選び、自分とその人の役職を入れ替えます。人狼陣営と入れ替わった場合、お互いの勝利条件も入れ替わります。\n\n【村人陣営の勝利条件】\n人狼を吊ることができれば勝利です。ただし、吊人を吊ってしまった場合はその時点で敗北となります。\n\n人狼が吊られなければ勝利です。ただし、吊人が吊られた　場合は吊人の単独勝利となります。"
    override val symbol: Int = R.drawable.thief
    override val defaultPlayers: Map<Int, Int> = mapOf(3 to 0, 4 to 1, 5 to 1, 6 to 1)
    override val isRequired: Boolean = false

    override fun getMiniMessage(cards: List<Card>): String? = null

    override fun ability(cards: MutableList<Card>, selectedKey: String): MutableList<Card> {
        val me = trueOwner
        val target = cards.find { it.trueOwner?.id == selectedKey }?.trueOwner
        return cards.map {
            if (it.trueOwner?.id == me?.id) it.trueOwner = target
            else if (it.trueOwner?.id == target?.id) it.trueOwner = me
            it
        }.toMutableList()
    }

    override fun abilityResult(cards: MutableList<Card>, key: String, context: Context): View? {
        val root = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(
                dp2px(24, context.resources),
                dp2px(24, context.resources),
                dp2px(24, context.resources),
                dp2px(12, context.resources)
            )
        }
        root.addView(TextView(context).apply {
            text = "あなたは"
            textSize = 24f
            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            width = ViewGroup.LayoutParams.MATCH_PARENT
        })
        root.addView(TextView(context).apply {
            text = "${cards.find { it.trueOwner?.id == owner?.id }?.name}"
            textSize = 48f
            setPadding(0, dp2px(8, context.resources), 0, dp2px(8, context.resources))
            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            setTextColor(Color.parseColor("#000000"))
            width = ViewGroup.LayoutParams.MATCH_PARENT
        })
        root.addView(TextView(context).apply {
            text = "になりました"
            textSize = 24f
            textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            width = ViewGroup.LayoutParams.MATCH_PARENT
        })
        return root
    }

    private fun dp2px(dp: Int, resources: Resources) =
        (dp * resources.displayMetrics.density + 0.5f).toInt()

    override fun hasAbility(): Boolean = true
    override fun shouldSelectList(): Boolean = true
    override fun getSelectList(cards: MutableList<Card>): Map<String, String>? {
        val map = mutableMapOf<String, String>()
        cards
            .filter { it.owner != null }
            .filter { it.owner?.id != owner?.id }
            .forEach { map["${it.owner?.id}"] = "${it.owner?.name}" }
        return map
    }

    override fun getSelectMessage(): String? = "入れ替わる人を選択してください"
}