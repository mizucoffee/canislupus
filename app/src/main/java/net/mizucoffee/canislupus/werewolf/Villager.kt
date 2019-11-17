package net.mizucoffee.canislupus.werewolf

import android.content.Context
import android.view.View
import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.enumerate.Camp
import net.mizucoffee.canislupus.enumerate.Camp.*
import net.mizucoffee.canislupus.enumerate.PositionEnum

open class Villager : Position() {

    override val name: String = "村人"
    override val camp: Camp = VILLAGER
    override var vote: String? = null
    override var player: Player? = null
    override val position: PositionEnum = PositionEnum.VILLAGER
    override val description: String =
        "【特殊能力】\n特殊能力はありません。\n\n【村人陣営の勝利条件】\n人狼を吊ることができれば勝利です。ただし、吊人を吊ってしまった場合はその時点で敗北となります。"

    override fun getMiniMessage(positions: List<Position>): String? = null

    override fun checkWinLose(executed: List<Position>, positions: List<Position>): Int {
        return when {
            executed.hasCamp(TANNER) -> 0
            executed.hasCamp(WEREWOLF) -> 1
            !positions.hasCamp(WEREWOLF) && positions.hasCamp(TANNER) && executed.isEmpty() -> 2
            !positions.hasCamp(WEREWOLF) && positions.hasCamp(TANNER) && executed.isNotEmpty() -> 1
            !positions.hasCamp(WEREWOLF) && !positions.hasCamp(TANNER) && executed.isEmpty() -> 2
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