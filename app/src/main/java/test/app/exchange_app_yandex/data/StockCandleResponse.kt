package test.app.exchange_app_yandex.data

import com.google.gson.annotations.SerializedName

data class StockCandleResponse(

        @SerializedName("c")
        var current: List<Float>,

        @SerializedName("h")
        var high: List<Float>,

        @SerializedName("l")
        var low: List<Float>,

        @SerializedName("o")
        var open: List<Float>,

        @SerializedName("s")
        var status: String,

        @SerializedName("t")
        var time: List<Int>,

        @SerializedName("v")
        var volume: List<Int>?
) {
}