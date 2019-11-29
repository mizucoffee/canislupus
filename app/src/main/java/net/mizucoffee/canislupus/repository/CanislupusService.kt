package net.mizucoffee.canislupus.repository

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CanislupusService {
    companion object {
        fun createService(): ICanislupusApi {
            val baseApiUrl = "http://canislupus.mizucoffee.com/api/"

            val httpLogging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClientBuilder = OkHttpClient.Builder().addInterceptor(httpLogging)

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseApiUrl)
                .client(httpClientBuilder.build())
                .build()

            return retrofit.create(ICanislupusApi::class.java)
        }
    }
}

fun <T> Call<T>.simpleCall() {
    this.enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) {}
        override fun onResponse(c: Call<T>, r: Response<T>) {}
    })
}