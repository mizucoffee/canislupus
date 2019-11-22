package net.mizucoffee.canislupus.model.werewolf

import android.content.Context
import android.view.View
import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.enumerate.Camp
import net.mizucoffee.canislupus.enumerate.CardEnum
import net.mizucoffee.canislupus.enumerate.Winner

abstract class Card {
    abstract val name: String
    abstract var owner: Player?
    abstract var trueOwner: Player?
    abstract var vote: String?
    abstract val card: CardEnum
    abstract val camp: Camp
    abstract val description: String
    abstract val symbol: Int
    abstract val defaultPlayers: Map<Int, Int>
    abstract val isRequired: Boolean

    abstract fun getMiniMessage(cards: List<Card>): String?
    abstract fun checkWinLose(executed: List<Card>, cards: List<Card>): Int
    abstract fun ability(cards: MutableList<Card>, selectedKey: String): MutableList<Card>
    abstract fun abilityResult(cards: MutableList<Card>, key: String, context: Context): View?
    abstract fun hasAbility(): Boolean
    abstract fun shouldSelectList(): Boolean
    abstract fun getSelectList(cards: MutableList<Card>): Map<String, String>?
    abstract fun getSelectMessage(): String?

    override fun toString(): String {
        return "[Card = \"$card\", Player = \"${owner?.name}\", TruePlayer = \"${trueOwner?.name}\", Camp = \"$camp\"]"
    }

    companion object {
        fun checkWinner(executed: List<Card>, cards: List<Card>): Winner {
            val players = cards.filter { it.owner != null }
            if (executed.hasCamp(Camp.TANNER)) return Winner.TANNER_NORMAL
            if (players.hasCamp(Camp.WEREWOLF)) {
                if (executed.hasCamp(Camp.WEREWOLF)) {
                    return Winner.VILLAGER_NORMAL
                } else {
                    if (executed.hasCamp(Camp.MADMAN)) return Winner.WEREWOLF_MADMAN
                    return Winner.WEREWOLF_NORMAL
                }
            } else {
                if (players.hasCamp(Camp.TANNER)) return Winner.VILLAGER_TANNER
                if (executed.isEmpty()) return Winner.VILLAGER_PEACE
                return Winner.WEREWOLF_PEACE
            }
        }
    }
}
