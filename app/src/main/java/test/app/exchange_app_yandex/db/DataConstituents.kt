package test.app.exchange_app_yandex.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "index_constituents")
data class DataConstituents(

    @PrimaryKey
    @ColumnInfo(name = "constituents")
    val constituents: String,

    @ColumnInfo(name = "symbol")
    val symbol: String
) {
}