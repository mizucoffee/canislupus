package net.mizucoffee.canislupus.werewolf

import android.content.Context
import android.view.View
import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.enumerate.Camp
import net.mizucoffee.canislupus.enumerate.Camp.*
import net.mizucoffee.canislupus.enumerate.CardEnum

class Madman : Card() {

    override var trueOwner: Player? = null
    override val name: String = "狂人"
    override val camp: Camp = MADMAN
    override var vote: String? = null
    override var owner: Player? = null
    override val card: CardEnum = CardEnum.MADMAN
    override val description: String =
        "【特殊能力】\n人狼陣営の村人で、特殊能力はありません。\n自分が殺されても人狼が生き残れば勝利となります。\n\n【人狼陣営の勝利条件】\n人狼が吊られなければ勝利です。ただし、吊人が吊られた　場合は吊人の単独勝利となります。"
    override val symbol: Int = R.drawable.madman
    override val defaultPlayers: Map<Int, Int> = mapOf(3 to 1, 4 to 1, 5 to 1, 6 to 1)
    override val isRequired: Boolean = false

    override fun getMiniMessage(cards: List<Card>): String? = null
    override fun checkWinLose(executed: List<Card>, cards: List<Card>): Int {
        return when {
            executed.hasCamp(TANNER) -> 0
            cards.hasCamp(WEREWOLF) && executed.hasCamp(WEREWOLF) -> 0
            cards.hasCamp(WEREWOLF) && executed.hasCamp(MADMAN) -> 2
            cards.hasCamp(WEREWOLF) -> 1
            cards.hasCamp(TANNER) && executed.isEmpty() -> 2
            cards.hasCamp(TANNER) -> 1
            executed.isEmpty() -> 2
            else -> 0
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