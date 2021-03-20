package test.app.exchange_app_yandex.data

import com.google.gson.annotations.SerializedName

data class MarketNewsResponse(

        @SerializedName("category")
        var category: String?,

        @SerializedName("datetime")
        var date_time: Int?,

        @SerializedName("headline")
        var head_line: String?,

        @SerializedName("id")
        var id: Int?,

        @SerializedName("image")
        var image: String?,

        @SerializedName("related")
        var related: String?,

        @SerializedName("source")
        var source: String?,

        @SerializedName("summary")
        var summary: String?,

        @SerializedName("url")
        var url: String?

) {
}