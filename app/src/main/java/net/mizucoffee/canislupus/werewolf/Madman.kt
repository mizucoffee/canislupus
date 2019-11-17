package net.mizucoffee.canislupus.werewolf

import android.content.Context
import android.view.View
import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.enumerate.Camp
import net.mizucoffee.canislupus.enumerate.Camp.*
import net.mizucoffee.canislupus.werewolf.Position
import net.mizucoffee.canislupus.enumerate.PositionEnum
import net.mizucoffee.canislupus.werewolf.hasCamp

class Madman : Position() {

    override val name: String = "狂人"
    override val camp: Camp = MADMAN
    override var vote: String? = null
    override var player: Player? = null
    override val position: PositionEnum = PositionEnum.MADMAN
    override val description: String =
        "【特殊能力】\n人狼陣営の村人で、特殊能力はありません。\n自分が殺されても人狼が生き残れば勝利となります。\n\n【人狼陣営の勝利条件】\n人狼が吊られなければ勝利です。ただし、吊人が吊られた　場合は吊人の単独勝利となります。"

    override fun getMiniMessage(positions: List<Position>): String? = null
    override fun checkWinLose(executed: List<Position>, positions: List<Position>): Int {
        return when {
            executed.hasCamp(TANNER) -> 0
            positions.hasCamp(WEREWOLF) && executed.hasCamp(WEREWOLF) -> 0
            positions.hasCamp(WEREWOLF) && executed.hasCamp(MADMAN) -> 2
            positions.hasCamp(WEREWOLF) -> 1
            positions.hasCamp(TANNER) && executed.isEmpty() -> 2
            positions.hasCamp(TANNER) -> 1
            executed.isEmpty() -> 2
            else -> 0
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