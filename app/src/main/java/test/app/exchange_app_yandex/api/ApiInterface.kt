package test.app.exchange_app_yandex.api

import retrofit2.http.GET
import retrofit2.http.Query
import io.reactivex.Single
import io.reactivex.Observable
import test.app.exchange_app_yandex.data.IndicesConstituentsResponse
import test.app.exchange_app_yandex.data.QuoteResponse
import test.app.exchange_app_yandex.data.StockProfileTwoResponse

interface ApiInterface {

    @GET("index/constituents")
    fun getIndicesConstituents(
        @Query("symbol") symbol: String,
        @Query("token") token: String
    ): Single<IndicesConstituentsResponse>

    @GET("quote")
    fun getQuote(
        @Query("symbol") apiKey: String,
        @Query("token") token: String
    ): Single<QuoteResponse>

    @GET("stock/candle")
    fun getStockCandle(
        @Query("symbol") apiKey: String,
        @Query("resolution") resolution: String,
        @Query("from") from: Long,
        @Query("to") to: Long,
        @Query("token") token: String
    )

    @GET("stock/profile2")
    fun getStockProfileTwo(
        @Query("symbol") apiKey: String,
        @Query("token") token: String
    ): Single<StockProfileTwoResponse>

}