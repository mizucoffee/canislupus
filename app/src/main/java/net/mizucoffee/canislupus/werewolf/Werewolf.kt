package net.mizucoffee.canislupus.werewolf

import android.content.Context
import android.view.View
import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.enumerate.Camp
import net.mizucoffee.canislupus.enumerate.Camp.*
import net.mizucoffee.canislupus.enumerate.CardEnum

open class Werewolf : Card() {

    override var trueOwner: Player? = null
    override val name: String = "人狼"
    override val camp: Camp = WEREWOLF
    override var vote: String? = null
    override var owner: Player? = null
    override val card: CardEnum = CardEnum.WEREWOLF
    override val description: String =
        "【特殊能力】\n特殊能力はありません。\n\n【人狼陣営の勝利条件】\n人狼が吊られなければ勝利です。ただし、吊人が吊られた　場合は吊人の単独勝利となります。"
    override val symbol: Int = R.drawable.werewolf
    override val defaultPlayers: Map<Int, Int> = mapOf(3 to 1, 4 to 2, 5 to 2, 6 to 2)
    override val isRequired: Boolean = true

    override fun getMiniMessage(cards: List<Card>): String? {
        val wolf = cards.filter { it.card == CardEnum.WEREWOLF }
            .filter { it.owner != null }
            .filter { it != this }
        return if (wolf.isNotEmpty())
            wolf.map { it.owner?.name }.joinToString("さんと") + "さんも人狼です"
        else
            "人狼はあなただけです"
    }

    override fun checkWinLose(executed: List<Card>, cards: List<Card>): Int {
        return when {
            executed.hasCamp(TANNER) -> 0
            executed.hasCamp(WEREWOLF) -> 0
            else -> 1
        }
    }

    override fun ability(cards: MutableList<Card>, selectedKey: String): MutableList<Card> = cards
    override fun abilityResult(cards: MutableList<Card>, key: String, context: Context): View? =
        null

    override fun hasAbility(): Boolean = false
    override fun shouldSelectList(): Boolean = false
    override fun getSelectList(cards: MutableList<Card>): Map<String, String>? = null
    override fun getSelectMessage(): String? = null
}