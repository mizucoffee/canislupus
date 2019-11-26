package net.mizucoffee.canislupus.model

data class ApiResponse<T: Any>(
    val ok: Boolean,
    val data: T
)