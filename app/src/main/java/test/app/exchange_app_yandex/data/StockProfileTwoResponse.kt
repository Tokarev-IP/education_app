package test.app.exchange_app_yandex.data

data class StockProfileTwoResponse(

    val country: String,
    val currency: String,
    val exchange: String,
    val ipo: String,
    val marketCapitalization: Int,
    val name: String,
    val phone: String,
    val shareOutstanding: Float,
    val ticker: String,
    val weburl: String,
    val logo: String,
    val finnhubIndustry: String
) {
}