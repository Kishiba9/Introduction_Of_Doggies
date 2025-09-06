package com.example.iods.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DogDao {

    @Query("select * from dogs order by name ASC")
    fun getAllDogs(): Flow<List<Dog>>

    @Query("select * from dogs where id = :id")
    fun getDog(id: Int): Flow<Dog>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(dog: Dog)

    @Update
    suspend fun update(dog: Dog)

    @Delete
    suspend fun delete(dog: Dog)
}