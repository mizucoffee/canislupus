package net.mizucoffee.canislupus.model

data class Response<T: Any>(
    val status: Int,
    val data: T
)