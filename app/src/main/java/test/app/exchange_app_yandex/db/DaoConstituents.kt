package test.app.exchange_app_yandex.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single
import test.app.exchange_app_yandex.data.IndicesConstituentsResponse

@Dao
interface DaoConstituents {

    @Query("SELECT * FROM index_constituents" )
    fun getAll(): Single<DataConstituents>

    @Query("SELECT constituents FROM index_constituents" )
    fun getConstituents(): Single<List<String>>

//    @Insert
//    fun insertConstituents(constituents: IndicesConstituentsResponse)

}