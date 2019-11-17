package net.mizucoffee.canislupus.werewolf

import android.content.Context
import android.view.View
import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.enumerate.Camp
import net.mizucoffee.canislupus.enumerate.Camp.*
import net.mizucoffee.canislupus.werewolf.Position
import net.mizucoffee.canislupus.enumerate.PositionEnum
import net.mizucoffee.canislupus.werewolf.hasCamp

open class Werewolf : Position() {

    override val name: String = "人狼"
    override val camp: Camp = WEREWOLF
    override var vote: String? = null
    override var player: Player? = null
    override val position: PositionEnum = PositionEnum.WEREWOLF
    override val description: String =
        "【特殊能力】\n特殊能力はありません。\n\n【人狼陣営の勝利条件】\n人狼が吊られなければ勝利です。ただし、吊人が吊られた　場合は吊人の単独勝利となります。"

    override fun getMiniMessage(positions: List<Position>): String? {
        val wolf = positions.filter { it.position == PositionEnum.WEREWOLF }
            .filter { it.player != null }
            .filter { it != this }
        for (position in wolf) {
            println(position)
        }
        return if (wolf.isNotEmpty())
            wolf.map { it.player?.name }.joinToString("さんと") + "さんも人狼です"
        else
            "人狼はあなただけです"
    }

    override fun checkWinLose(executed: List<Position>, positions: List<Position>): Int {
        return when {
            executed.hasCamp(TANNER) -> 0
            executed.hasCamp(WEREWOLF) -> 0
            else -> 1
        }
    }

    override fun ability(positions: MutableList<Position>, selected: Int): List<Position> =
        positions

    override fun abilityResult(
        positions: MutableList<Position>,
        selectedKey: String,
        context: Context
    ): View? = null

    override fun hasAbility(): Boolean = false
    override fun getSelectList(positions: MutableList<Position>): Map<String, String>? = null
    override fun getSelectMessage(): String? = null
}