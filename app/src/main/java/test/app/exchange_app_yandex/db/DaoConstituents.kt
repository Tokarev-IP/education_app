package test.app.exchange_app_yandex.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single

@Dao
interface DaoConstituents {

    @Query("SELECT * FROM index_constituents" )
    fun getAllConstituents(): Single<DataConstituents>

    @Query("SELECT * FROM quote WHERE constituent = :constituent" )
    fun getQuote(constituent: String): Single<DataQuote>

    @Query("SELECT * FROM stock_profil WHERE constituent = :constituent" )
    fun getProfil(constituent: String): Single<DataStockProfileTwo>

    @Query("SELECT constituents FROM index_constituents" )
    fun getConstituents(): Single<List<String>>

    @Insert
    fun insertConstituents(constituents: DataConstituents)

    @Insert
    fun insertQuote(constituents: DataQuote)

    @Insert
    fun insertStockProfil(constituents: DataStockProfileTwo)

}