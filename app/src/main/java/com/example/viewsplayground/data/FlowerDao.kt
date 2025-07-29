package com.example.viewsplayground.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface FlowerDao {
    //存在就替换，相当于update
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(flower: Flower): Long

    @Query("DELETE FROM flowers WHERE id = :id")
    suspend fun delete(id: UUID)

    @Query("SELECT * FROM flowers")
    fun getAll(): Flow<List<Flower>>

    @Query("SELECT * FROM flowers WHERE id == :id")
    fun getFlower(id: UUID): Flow<Flower>
}