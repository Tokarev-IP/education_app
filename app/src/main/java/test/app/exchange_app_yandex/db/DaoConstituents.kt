package test.app.exchange_app_yandex.db

import android.text.BoringLayout
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Single
import io.reactivex.Completable

@Dao
interface DaoConstituents {

    @Query("SELECT * FROM index_constituents ORDER BY constituents ASC" )
    fun getAllConstituents(): Single<List<DataConstituents>>

    @Query("SELECT * FROM index_constituents ORDER BY constituents ASC" )
    fun getFactoryAllConstituents(): DataSource.Factory<Int, DataConstituents>

    @Query("SELECT * FROM index_constituents WHERE favorite = 'true' ORDER BY constituents ASC" )
    fun getFavoriteAllConstituents(): Single<List<DataConstituents>>

    @Query("SELECT * FROM quote WHERE constituent = :constituent" )
    fun getQuote(constituent: String): Single<List<DataQuote>>

    @Query("SELECT * FROM stock_profil WHERE constituent = :constituent" )
    fun getStockProfilTWO(constituent: String): Single<List<DataStockProfileTwo>>

    @Query("SELECT * FROM index_constituents WHERE constituents LIKE ''||:data||'%'" )
    fun getConstituent(data: String): DataSource.Factory<Int, DataConstituents>

    @Query("DELETE FROM quote")
    fun deleteAllQuote():Completable

    @Query("UPDATE index_constituents SET favorite = :bool WHERE constituents = :constituents")
    fun update(constituents: String, bool: Boolean): Completable

    @Update
    fun updateFav(fav: DataConstituents): Completable

    @Update
    fun updateQuote(constituent: DataQuote): Completable

    @Insert
    fun insertConstituents(constituent: DataConstituents): Completable

    @Insert
    fun insertQuote(constituent: DataQuote): Completable

    @Insert
    fun insertStockProfilTwo(constituent: DataStockProfileTwo): Completable

}