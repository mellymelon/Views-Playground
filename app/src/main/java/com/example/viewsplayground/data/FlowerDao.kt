package com.example.viewsplayground.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FlowerDao {
    @Insert
    suspend fun add(flower: Flower): Long

    @Query("DELETE FROM flowers WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM flowers")
    fun getAll(): Flow<List<Flower>>

    @Query("SELECT * FROM flowers WHERE id == :id")
    fun getFlower(id: Long): Flow<Flower>
}