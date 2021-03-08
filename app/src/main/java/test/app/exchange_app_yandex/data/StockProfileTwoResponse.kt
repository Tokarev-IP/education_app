package test.app.exchange_app_yandex.data

import com.google.gson.annotations.SerializedName

data class StockProfileTwoResponse(

    @SerializedName("country")
    val country: String,

    @SerializedName("currency")
    val currency: String,

    @SerializedName("exchange")
    val exchange: String,

    @SerializedName("ipo")
    val ipo: String,

    @SerializedName("marketCapitalization")
    val market_capitalization: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("shareOutstanding")
    val share_outstanding: Float,

    @SerializedName("ticker")
    val ticker: String,

    @SerializedName("weburl")
    val web_url: String,

    @SerializedName("logo")
    val logo: String,

    @SerializedName("finnhubIndustry")
    val finnhub_industry: String
) {
}