package com.example.hackernews.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StoriesDao {

    @Insert
    fun addStroires(storiesEntity: StoriesEntity)

//    @Query("SELECT * FROM StoriesEntity")
//    fun getAllData()
}