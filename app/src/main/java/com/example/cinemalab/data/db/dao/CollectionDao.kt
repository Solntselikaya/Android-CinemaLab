package com.example.cinemalab.data.db.dao

import androidx.room.*
import com.example.cinemalab.data.db.entities.CollectionEntity

@Dao
interface CollectionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(collections: CollectionEntity)

    @Update
    suspend fun updateCollection(collection: CollectionEntity)

    @Query("SELECT * FROM collections")
    fun getAll(): List<CollectionEntity>

    @Delete
    suspend fun delete(collection: CollectionEntity)

    @Query("DELETE FROM collections")
    suspend fun deleteAll()
}