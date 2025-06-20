package com.example.viewsplayground.data

import jakarta.inject.Inject

class FlowerRepository @Inject constructor(private val flowerDao: FlowerDao) {
    fun getFlowers() = flowerDao.getAll()

    fun getFlower(id: Long) = flowerDao.getFlower(id)

    suspend fun add(flower: Flower) = flowerDao.add(flower)

    suspend fun delete(id: Long) = flowerDao.delete(id)
}