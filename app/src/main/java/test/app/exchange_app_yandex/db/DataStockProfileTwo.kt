package test.app.exchange_app_yandex.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "stock_profil")
data class DataStockProfileTwo(

        @PrimaryKey
        @ColumnInfo(name = "constituent")
        val constituent: String,

        @ColumnInfo(name = "country")
        val country: String,

        @ColumnInfo(name = "currency")
        val currency: String,

        @ColumnInfo(name = "name")
        val name: String,

        @ColumnInfo(name = "market_capitalization")
        val marketCapitalization: Float,

        @ColumnInfo(name = "web_url")
        val weburl: String,

        @ColumnInfo(name = "logo")
        val logo: String,

        @ColumnInfo(name = "finnhub_Industry")
        val finnhubIndustry: String,

) {
}