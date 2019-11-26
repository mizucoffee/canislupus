package net.mizucoffee.canislupus.repository

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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