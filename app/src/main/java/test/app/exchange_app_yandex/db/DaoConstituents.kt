package test.app.exchange_app_yandex.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single
import io.reactivex.Completable

@Dao
interface DaoConstituents {

    @Query("SELECT * FROM index_constituents ORDER BY constituents ASC" )
    fun getAllConstituents(): Single<List<DataConstituents>>

    @Query("SELECT * FROM quote WHERE constituent = :constituent" )
    fun getQuote(constituent: String): Single<List<DataQuote>>

    @Query("SELECT * FROM stock_profil WHERE constituent = :constituent" )
    fun getStockProfilTWO(constituent: String): Single<List<DataStockProfileTwo>>

    @Query("SELECT constituents FROM index_constituents" )
    fun getConstituents(): Single<List<String>>

    @Insert
    fun insertConstituents(constituent: DataConstituents): Completable

    @Insert
    fun insertQuote(constituent: DataQuote): Completable

    @Insert
    fun insertStockProfilTwo(constituent: DataStockProfileTwo): Completable

}