package net.mizucoffee.canislupus.werewolf

import android.content.Context
import android.view.View
import kotlinx.coroutines.handleCoroutineException
import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.enumerate.Camp
import net.mizucoffee.canislupus.enumerate.PositionEnum
import net.mizucoffee.canislupus.enumerate.Winner

abstract class Position {

    // 投票先とか管理
    // 1試合単位で使う情報を保管
    abstract val name: String
    abstract var player: Player? // = このカードの所有者
    abstract var truePlayer: Player?
    abstract var vote: String?
    abstract val position: PositionEnum
    abstract val camp: Camp
    abstract val description: String
    abstract val symbol: Int
    abstract val defaultPlayers: Map<Int, Int>
    abstract val isRequired: Boolean

    abstract fun getMiniMessage(positions: List<Position>): String?

    abstract fun checkWinLose(executed: List<Position>, positions: List<Position>): Int

    abstract fun ability(
        positions: MutableList<Position>,
        selectedKey: String
    ): MutableList<Position>

    abstract fun abilityResult(
        positions: MutableList<Position>,
        selectedKey: String,
        context: Context
    ): View?

    abstract fun hasAbility(): Boolean
    abstract fun shouldSelectList(): Boolean
    abstract fun getSelectList(positions: MutableList<Position>): Map<String, String>?
    abstract fun getSelectMessage(): String?

    override fun toString(): String {
        return "[Position = \"$position\", Player = \"${player?.name}\", TruePlayer = \"${truePlayer?.name}\", Camp = \"$camp\"]"
    }

    companion object {
        val defaultSet = mapOf(
            3 to mapOf(
                PositionEnum.WEREWOLF to 1,
                PositionEnum.MADMAN to 1,
                PositionEnum.SEER to 1,
                PositionEnum.THIEF to 0,
                PositionEnum.TANNER to 0,
                PositionEnum.VILLAGER to 2
            ),
            4 to mapOf(
                PositionEnum.WEREWOLF to 2,
                PositionEnum.MADMAN to 1,
                PositionEnum.SEER to 1,
                PositionEnum.THIEF to 1,
                PositionEnum.TANNER to 0,
                PositionEnum.VILLAGER to 1
            ),
            5 to mapOf(
                PositionEnum.WEREWOLF to 2,
                PositionEnum.MADMAN to 1,
                PositionEnum.SEER to 1,
                PositionEnum.THIEF to 1,
                PositionEnum.TANNER to 1,
                PositionEnum.VILLAGER to 1
            ),
            6 to mapOf(
                PositionEnum.WEREWOLF to 2,
                PositionEnum.MADMAN to 1,
                PositionEnum.SEER to 2,
                PositionEnum.THIEF to 1,
                PositionEnum.TANNER to 1,
                PositionEnum.VILLAGER to 1
            )
        )

        val positionInit = mapOf(
            PositionEnum.WEREWOLF to { Werewolf() },
            PositionEnum.MADMAN to { Madman() },
            PositionEnum.SEER to { Seer() },
            PositionEnum.THIEF to { Thief() },
            PositionEnum.TANNER to { Tanner() },
            PositionEnum.VILLAGER to { Villager() }
        )

        fun checkWinner(executed: List<Position>, positions: List<Position>): Winner {
            val players = positions.filter { it.player != null }
            if (executed.hasCamp(Camp.TANNER)) return Winner.TANNER_NORMAL
            if (players.hasCamp(Camp.WEREWOLF)) {
                if (executed.hasCamp(Camp.WEREWOLF)) {
                    return Winner.VILLAGER_NORMAL
                } else {
                    if(executed.hasCamp(Camp.MADMAN)) return Winner.WEREWOLF_MADMAN
                    return Winner.WEREWOLF_NORMAL
                }
            } else { // 平和村
                if (players.hasCamp(Camp.TANNER)) return Winner.VILLAGER_TANNER
                if (executed.isEmpty()) return Winner.VILLAGER_PEACE
                return Winner.WEREWOLF_PEACE
            }
        }
    }

    // 勝敗判定時の調整（怪盗）関数
}
