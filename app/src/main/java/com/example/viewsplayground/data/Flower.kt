package com.example.viewsplayground.data

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flowers")
data class Flower(
    @PrimaryKey val id: Long,
    val name: String,
    @DrawableRes
    val image: Int?,
    val description: String
)