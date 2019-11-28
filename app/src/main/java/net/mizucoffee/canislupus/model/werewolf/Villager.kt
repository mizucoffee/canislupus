package net.mizucoffee.canislupus.model.werewolf

import android.content.Context
import android.view.View
import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.enumerate.Camp
import net.mizucoffee.canislupus.enumerate.Camp.*
import net.mizucoffee.canislupus.enumerate.CardEnum

open class Villager : Card() {

    override var trueOwner: Player? = null
    override val name: String = "村人"
    override val camp: Camp = VILLAGER
    override var vote: String? = null
    override var owner: Player? = null
    override val card: CardEnum = CardEnum.VILLAGER
    override val description: String =
        "【特殊能力】\n特殊能力はありません。\n\n【村人陣営の勝利条件】\n人狼を吊ることができれば勝利です。ただし、吊人を吊ってしまった場合はその時点で敗北となります。"
    override val symbol: Int = R.drawable.ic_villager
    override val defaultPlayers: Map<Int, Int> = mapOf(3 to 2, 4 to 1, 5 to 1, 6 to 1)
    override val isRequired: Boolean = false

    override fun getMiniMessage(cards: List<Card>): String? = null
    override fun checkWinLose(executed: List<Card>, cards: List<Card>): Int {
        return when {
            executed.hasCamp(TANNER) -> 0
            executed.hasCamp(WEREWOLF) -> 1
            !cards.hasCamp(WEREWOLF) && cards.hasCamp(TANNER) && executed.isEmpty() -> 2
            !cards.hasCamp(WEREWOLF) && cards.hasCamp(TANNER) && executed.isNotEmpty() -> 1
            !cards.hasCamp(WEREWOLF) && !cards.hasCamp(TANNER) && executed.isEmpty() -> 2
            else -> 0
        }
    }
    override fun ability(cards: MutableList<Card>, selectedKey: String): MutableList<Card> = cards
    override fun abilityResultView(cards: MutableList<Card>, key: String, context: Context): View? =
        null
    override fun abilityResultText(): String? = null
    override fun hasAbility(): Boolean = false
    override fun shouldSelectList(): Boolean = false
    override fun getSelectList(cards: MutableList<Card>): Map<String, String>? = null
    override fun getSelectMessage(): String? = null
}