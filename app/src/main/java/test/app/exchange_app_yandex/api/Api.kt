package test.app.exchange_app_yandex.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Api {

    const val BASE_URL = "https://finnhub.io/api/v1/"

    val apiClient: ApiInterface by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return@lazy retrofit.create(ApiInterface::class.java)
    }
}