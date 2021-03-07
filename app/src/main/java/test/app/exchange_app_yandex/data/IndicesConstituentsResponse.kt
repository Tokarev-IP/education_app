package test.app.exchange_app_yandex.data

import com.google.gson.annotations.SerializedName

data class IndicesConstituentsResponse(

    @SerializedName("constituents")
    var constituents: List<String>,

    @SerializedName("symbol")
    var symbol: String
) {
}