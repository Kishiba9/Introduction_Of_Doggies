package com.example.iods.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dogs")
data class Dog(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val age: String,
    val hobby: String,
    val freeComment: String?,
    val imageUri: String? = null // 画像のURIを保存するフィールド (Nullableにするのが一般的)
)