package test.app.exchange_app_yandex.api

import retrofit2.http.GET
import retrofit2.http.Query
import io.reactivex.Single
import test.app.exchange_app_yandex.data.*

interface ApiInterface {

    @GET("index/constituents")
    fun getIndicesConstituents(
        @Query("symbol") symbol: String,
        @Query("token") token: String
    ): Single<IndicesConstituentsResponse>

    @GET("quote")
    fun getQuote(
        @Query("symbol") symbol: String,
        @Query("token") token: String
    ): Single<QuoteResponse>

    @GET("stock/candle")
    fun getStockCandle(
        @Query("symbol") symbol: String,
        @Query("resolution") resolution: String,
        @Query("from") from: Long,
        @Query("to") to: Long,
        @Query("token") token: String
    ): Single<StockCandleResponse>

    @GET("stock/profile2")
    fun getStockProfileTwo(
        @Query("symbol") symbol: String,
        @Query("token") token: String
    ): Single<StockProfileTwoResponse>

    @GET("news")
    fun getNews(
            @Query("category") category: String,
            @Query("token") token: String
    ): Single<List<MarketNewsResponse>>

}