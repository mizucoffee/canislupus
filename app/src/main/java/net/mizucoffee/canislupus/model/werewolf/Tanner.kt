package net.mizucoffee.canislupus.model.werewolf

import android.content.Context
import android.view.View
import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.enumerate.Camp
import net.mizucoffee.canislupus.enumerate.Camp.*
import net.mizucoffee.canislupus.enumerate.CardEnum

class Tanner : Card() {

    override var trueOwner: Player? = null
    override val name: String = "吊人"
    override val camp: Camp = TANNER
    override var vote: String? = null
    override var owner: Player? = null
    override val card: CardEnum = CardEnum.TANNER
    override val description: String =
        "【特殊能力】\n自分が吊られたら勝利です。\n\n【第三陣営の勝利条件】\n自分が吊られたら勝利となります。他のどの陣営にも属しません。"
    override val symbol: Int = R.drawable.tanner
    override val defaultPlayers: Map<Int, Int> = mapOf(3 to 0, 4 to 0, 5 to 1, 6 to 1)
    override val isRequired: Boolean = false

    override fun getMiniMessage(cards: List<Card>): String? = null
    override fun checkWinLose(executed: List<Card>, cards: List<Card>): Int {
        return if (executed.hasCamp(TANNER)) 3 else 0
    }

    override fun ability(cards: MutableList<Card>, selectedKey: String): MutableList<Card> =
        cards

    override fun abilityResult(cards: MutableList<Card>, key: String, context: Context): View? =
        null

    override fun hasAbility(): Boolean = false
    override fun shouldSelectList(): Boolean = false
    override fun getSelectList(cards: MutableList<Card>): Map<String, String>? = null
    override fun getSelectMessage(): String? = null
}