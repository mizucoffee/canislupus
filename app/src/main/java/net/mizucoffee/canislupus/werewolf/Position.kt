package net.mizucoffee.canislupus.werewolf

import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.werewolf.enumerate.Camp
import net.mizucoffee.canislupus.werewolf.enumerate.PositionEnum
import net.mizucoffee.canislupus.werewolf.enumerate.Winner
import net.mizucoffee.canislupus.werewolf.position.*

abstract class Position {

    // 投票先とか管理
    // 1試合単位で使う情報を保管
    abstract val player: Player?
    abstract var vote: String?

    abstract val camp: Camp

    abstract fun checkWinLose(executed: List<Position>, positions: List<Position>): Int

    abstract fun ability(positions: List<Position>): String?

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
            return Winner.VILLAGER_NORMAL
        }
    }

    // 勝敗判定時の調整（怪盗）関数
}