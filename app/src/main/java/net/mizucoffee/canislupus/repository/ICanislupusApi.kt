package net.mizucoffee.canislupus.repository

import net.mizucoffee.canislupus.model.*
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

    @POST("game/init")
    fun initGame(): Call<ApiResponse<GameInit>>

    @POST("game/set")
    @FormUrlEncoded
    fun postGame(
        @Field("id") id: String,
        @Field("phase") phase: Int,
        @Field("data") data: String
    ): Call<ApiResponse<Game>>

}