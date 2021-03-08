package test.app.exchange_app_yandex.data

import com.google.gson.annotations.SerializedName

data class QuoteResponse(

    @SerializedName("c")
    val current: Float,

    @SerializedName("h")
    val high: Float,

    @SerializedName("l")
    val low: Float,

    @SerializedName("o")
    val open: Float,

    @SerializedName("pc")
    val previous_close: Float,

    @SerializedName("t")
    val t: Float
) {
}