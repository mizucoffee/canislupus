package net.mizucoffee.canislupus.model.werewolf

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import android.widget.TextView
import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.enumerate.CardEnum

class Seer : Villager() {
    override val name: String = "占師"
    override val card: CardEnum = CardEnum.SEER
    override val description: String =
        "【特殊能力】\n自分以外の誰か1人、または余っているカード2枚を見る事ができます。\n\n【村人陣営の勝利条件】\n人狼を吊ることができれば勝利です。ただし、吊人を吊ってしまった場合はその時点で敗北となります。"
    override val symbol: Int = R.drawable.ic_seer
    override val defaultPlayers: Map<Int, Int> = mapOf(3 to 1, 4 to 1, 5 to 1, 6 to 1)
    override val isRequired: Boolean = false

    var resMes = ""

    override fun getMiniMessage(cards: List<Card>): String? = null
    override fun ability(cards: MutableList<Card>, selectedKey: String): MutableList<Card> = cards
    override fun abilityResultView(cards: MutableList<Card>, key: String, context: Context): View? {
        val root = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(
                dp2px(24, context.resources),
                dp2px(24, context.resources),
                dp2px(24, context.resources),
                dp2px(12, context.resources)
            )
        }
        val pos = cards.find { it.owner?.id == key }
        pos?.also { p ->
            p.owner?.also {
                resMes = "${owner?.name} - 占い -> ${it.name}"
                root.addView(TextView(context).apply {
                    text = "${it.name}さんは"
                    textSize = 24f
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    width = MATCH_PARENT
                })
                root.addView(TextView(context).apply {
                    text = "${p.name}"
                    textSize = 48f
                    setPadding(0, dp2px(8, context.resources), 0, dp2px(8, context.resources))
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    setTextColor(Color.parseColor("#000000"))
                    width = MATCH_PARENT
                })
                root.addView(TextView(context).apply {
                    text = "でした"
                    textSize = 24f
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    width = MATCH_PARENT
                })
            }
        }
        if (pos == null) {
            resMes = "${owner?.name} - 占い -> 場のカード"
            val list = cards.filter { it.owner == null }.map { it.name }
            root.addView(TextView(context).apply {
                text = "場のカードは"
                textSize = 24f
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                width = MATCH_PARENT
            })
            root.addView(TextView(context).apply {
                text = "${list.joinToString("と")}"
                textSize = 48f
                setPadding(0, dp2px(8, context.resources), 0, dp2px(8, context.resources))
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                setTextColor(Color.parseColor("#000000"))
                width = MATCH_PARENT
            })
            root.addView(TextView(context).apply {
                text = "でした"
                textSize = 24f
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                width = MATCH_PARENT
            })
        }
        return root
    }

    private fun dp2px(dp: Int, resources: Resources) =
        (dp * resources.displayMetrics.density + 0.5f).toInt()

    override fun abilityResultText(): String? = resMes
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