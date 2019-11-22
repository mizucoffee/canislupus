package net.mizucoffee.canislupus.model.werewolf

import net.mizucoffee.canislupus.enumerate.Camp

fun <T: Any> List<T>.has(equals :(obj: T) -> Boolean): Boolean = this.indexOfFirst { equals(it) } >= 0
fun List<Card>.hasCamp(camp: Camp): Boolean = this.has { it.camp == camp }