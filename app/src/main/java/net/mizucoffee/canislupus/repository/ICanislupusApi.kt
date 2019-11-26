package net.mizucoffee.canislupus.repository

import net.mizucoffee.canislupus.model.RPlayer
import net.mizucoffee.canislupus.model.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ICanislupusApi {
    @GET("api/player/auth")
    fun playerAuth(@Query("id") id: String, @Query( "verify") verify: String): Call<Response<RPlayer>>
}