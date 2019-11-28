package net.mizucoffee.canislupus.model

import java.io.Serializable

data class Error(
    val code: Int,
    val msg: String
): Serializable