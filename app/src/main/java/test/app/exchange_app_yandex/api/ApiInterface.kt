package test.app.exchange_app_yandex.api

import retrofit2.http.GET
import retrofit2.http.Query
import io.reactivex.Single
import io.reactivex.Observable

interface ApiInterface {

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<Int>

}