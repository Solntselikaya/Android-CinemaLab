package com.example.cinemalab.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cinemalab.data.db.dao.CollectionDao
import com.example.cinemalab.data.db.entities.CollectionEntity

@Database(entities = [CollectionEntity::class], version = 1)
abstract class CollectionRoomDatabase : RoomDatabase() {

    abstract fun collectionDao(): CollectionDao

    /*companion object {
        @Volatile
        private var INSTANCE: CollectionRoomDatabase? = null

        fun getDatabase(context: Context): CollectionRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CollectionRoomDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }*/

}