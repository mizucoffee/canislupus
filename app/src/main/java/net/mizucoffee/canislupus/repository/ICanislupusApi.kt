package net.mizucoffee.canislupus.repository

import net.mizucoffee.canislupus.model.RPlayer
import net.mizucoffee.canislupus.model.ApiResponse
import retrofit2.Call
import retrofit2.http.*

interface ICanislupusApi {
    @POST("player/auth")
    @FormUrlEncoded
    fun playerAuth(@Field("qr") qr: String, @Field( "verify") verify: String): Call<ApiResponse<RPlayer>>
}