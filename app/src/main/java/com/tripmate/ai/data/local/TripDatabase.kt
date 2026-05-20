package com.tripmate.ai.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tripmate.ai.data.local.dao.TripDao
import com.tripmate.ai.data.local.entity.ItineraryDayEntity
import com.tripmate.ai.data.local.entity.PlaceEntity
import com.tripmate.ai.data.local.entity.TripEntity

@Database(
    entities = [TripEntity::class, ItineraryDayEntity::class, PlaceEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TripDatabase : RoomDatabase() {
    abstract fun tripDao(): TripDao

    companion object {
        @Volatile
        private var INSTANCE: TripDatabase? = null

        fun getDatabase(context: Context): TripDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TripDatabase::class.java,
                    "trip_mate_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
