package test.app.exchange_app_yandex.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single

@Dao
interface DaoConstituents {

    @Query("SELECT constituents FROM index_constituents" )
    fun getConstituents(): Single<List<String>>

    @Insert
    fun insertConstituents(constituents: List<String>)

    @Delete
    fun deleteConstituent(constituent: String)
}