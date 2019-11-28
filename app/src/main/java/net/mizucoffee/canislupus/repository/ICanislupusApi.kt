package net.mizucoffee.canislupus.repository

import net.mizucoffee.canislupus.model.Player
import net.mizucoffee.canislupus.model.ApiResponse
import net.mizucoffee.canislupus.model.Qr
import retrofit2.Call
import retrofit2.http.*

interface ICanislupusApi {

    @POST("player/auth")
    @FormUrlEncoded
    fun playerAuth(@Field("qr") qr: String, @Field("verify") verify: String): Call<ApiResponse<Player>>

    @POST("player")
    @FormUrlEncoded
    fun player(
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("qr") qr: String,
        @Field("verify") verify: String
    ): Call<ApiResponse<Player>>

    @GET("player/uniq")
    fun uniq(): Call<ApiResponse<Qr>>

    @POST("player/auth/pin")
    @FormUrlEncoded
    fun authPin(@Field("id") id: String): Call<ApiResponse<Qr>>

}