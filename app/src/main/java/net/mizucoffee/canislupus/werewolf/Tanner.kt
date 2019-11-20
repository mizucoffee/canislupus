package net.mizucoffee.canislupus.werewolf

import android.content.Context
import android.view.View
import net.mizucoffee.canislupus.R
import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.enumerate.Camp
import net.mizucoffee.canislupus.enumerate.Camp.*
import net.mizucoffee.canislupus.enumerate.PositionEnum

class Tanner : Position() {

    override var truePlayer: Player? = null
    override val name: String = "吊人"
    override val camp: Camp = TANNER
    override var vote: String? = null
    override var player: Player? = null
    override val position: PositionEnum = PositionEnum.TANNER
    override val description: String =
        "【特殊能力】\n自分が吊られたら勝利です。\n\n【第三陣営の勝利条件】\n自分が吊られたら勝利となります。他のどの陣営にも属しません。"
    override val symbol: Int = R.drawable.tanner
    override val defaultPlayers: Map<Int, Int> = mapOf(3 to 0, 4 to 0, 5 to 1, 6 to 1)
    override val isRequired: Boolean = false

    override fun getMiniMessage(positions: List<Position>): String? = null
    override fun checkWinLose(executed: List<Position>, positions: List<Position>): Int {
        return if (executed.hasCamp(TANNER)) 3 else 0
    }

    override fun ability(
        positions: MutableList<Position>,
        selectedKey: String
    ): MutableList<Position> =
        positions

    override fun abilityResult(
        positions: MutableList<Position>,
        selectedKey: String,
        context: Context
    ): View? = null

    override fun hasAbility(): Boolean = false
    override fun shouldSelectList(): Boolean = false
    override fun getSelectList(positions: MutableList<Position>): Map<String, String>? = null
    override fun getSelectMessage(): String? = null
}