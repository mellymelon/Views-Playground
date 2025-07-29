package com.example.viewsplayground.data

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "flowers")
data class Flower(
    @PrimaryKey val id: UUID,
    val name: String,
    @DrawableRes
    val image: Int?,
    val description: String
)