package com.example.viewsplayground

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.viewsplayground.data.Flower
import com.example.viewsplayground.data.FlowerDao

@Database(entities = [Flower::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getFlowerDao(): FlowerDao
}