package test.app.exchange_app_yandex.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataConstituents::class, DataStockProfileTwo::class, DataQuote::class], version = 1, exportSchema = false)
abstract class DbConstituents : RoomDatabase() {

    abstract fun movieDao(): DaoConstituents

    companion object {
        @Volatile
        private var INSTANCE: DbConstituents? = null

        fun getDatabase(context: Context): DbConstituents {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DbConstituents::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}