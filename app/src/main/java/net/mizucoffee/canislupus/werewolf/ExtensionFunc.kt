package net.mizucoffee.canislupus.werewolf

import net.mizucoffee.canislupus.werewolf.enumerate.Camp

fun <T: Any> List<T>.has(equals :(obj: T) -> Boolean): Boolean = this.indexOfFirst { equals(it) } >= 0
fun List<Position>.hasCamp(camp: Camp): Boolean = this.has { it.camp == camp }