package com.bryanalvarez.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bryanalvarez.data.local.dao.UserSearchDao
import com.bryanalvarez.domain.models.UserSearch

@Database(entities = [UserSearch::class], version = 3)
abstract class MLDataBase: RoomDatabase() {

    abstract fun userSearchDao(): UserSearchDao

    companion object {
        @Volatile
        private var INSTANCE: MLDataBase? = null

        /**
         * singleton function to return the database instance
         */
        fun getDatabase(context: Context): MLDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MLDataBase::class.java,
                    "ml_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}