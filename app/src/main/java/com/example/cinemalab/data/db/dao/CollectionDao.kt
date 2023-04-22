package com.example.cinemalab.data.db.dao

import androidx.room.*
import com.example.cinemalab.common.Constants.DATABASE_NAME
import com.example.cinemalab.data.db.entities.CollectionEntity

@Dao
interface CollectionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(collections: CollectionEntity)

    @Update
    suspend fun updateCollection(collection: CollectionEntity)

    @Query("SELECT * FROM $DATABASE_NAME WHERE user_id = :userId")
    fun getAllForUser(userId: String): List<CollectionEntity>

    @Query("DELETE FROM $DATABASE_NAME WHERE collection_id = :collectionId")
    suspend fun delete(collectionId: String)

    @Query("DELETE FROM $DATABASE_NAME WHERE user_id = :userId")
    suspend fun deleteAllForUser(userId: String)

    @Query("DELETE FROM $DATABASE_NAME")
    suspend fun deleteAll()
}