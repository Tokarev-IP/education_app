package test.app.exchange_app_yandex.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "quote")
data class DataQuote(

        @PrimaryKey
        @ColumnInfo(name = "constituent")
        val constituent: String,

        @ColumnInfo(name = "open")
        val open: Float,

        @ColumnInfo(name = "current")
        val current: Float,

        @ColumnInfo(name = "low")
        val low: Float,

        @ColumnInfo(name = "high")
        val high: Float,

) {
}