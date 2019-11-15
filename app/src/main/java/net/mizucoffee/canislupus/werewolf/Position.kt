package net.mizucoffee.canislupus.werewolf

import net.mizucoffee.canislupus.model.Player

abstract class Position {

    // 投票先とか管理
    // 1試合単位で使う情報を保管
    abstract val player: Player?
    abstract var vote: String?

    abstract val camp: Camp

    abstract fun checkWinLose(executed: List<Position>, positions: List<Position>): Int

    abstract fun ability(positions: List<Position>): String?

    // 勝敗判定時の調整（怪盗）関数
}