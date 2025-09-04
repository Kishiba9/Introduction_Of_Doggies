package com.example.woof.data

import kotlinx.coroutines.flow.Flow

interface DogRepository {

    fun getAllDogStream(): Flow<List<Dog>>

    fun getDogStream(id: Int): Flow<Dog?>

    suspend fun insertDog(dog: Dog)

    suspend fun updateDog(dog: Dog)

    suspend fun  deleteDog(dog: Dog)
}