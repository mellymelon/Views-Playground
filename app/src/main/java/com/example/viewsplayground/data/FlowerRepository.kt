package com.example.viewsplayground.data

import jakarta.inject.Inject
import java.util.UUID

class FlowerRepository @Inject constructor(private val flowerDao: FlowerDao) {
    fun getFlowers() = flowerDao.getAll()

    fun getFlower(id: UUID) = flowerDao.getFlower(id)

    suspend fun add(flower: Flower) = flowerDao.add(flower)

    suspend fun delete(id: UUID) = flowerDao.delete(id)
}