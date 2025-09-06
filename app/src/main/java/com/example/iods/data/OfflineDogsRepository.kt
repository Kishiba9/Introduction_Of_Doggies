package com.example.iods.data

import kotlinx.coroutines.flow.Flow

class OfflineDogsRepository(private val dogDao: DogDao): DogRepository {
    override fun getAllDogStream(): Flow<List<Dog>> = dogDao.getAllDogs()

    override fun getDogStream(id: Int): Flow<Dog> = dogDao.getDog(id)

    override suspend fun insertDog(dog: Dog) = dogDao.insert(dog)

    override suspend fun  updateDog(dog: Dog) = dogDao.update(dog)

    override suspend fun  deleteDog(dog: Dog) = dogDao.delete(dog)
}