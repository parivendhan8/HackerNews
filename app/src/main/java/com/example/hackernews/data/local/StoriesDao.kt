package com.example.hackernews.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoriesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addStories(storiesEntity: StoriesEntity)

    @Query("SELECT * FROM StoriesEntity")
    fun getStories(): LiveData<List<StoriesEntity>>

    @Query("SELECT * FROM StoriesEntity WHERE `by` LIKE :value OR time LIKE :value")
    fun search(value: String): List<StoriesEntity>

    @Query("SELECT storyId FROM StoriesEntity")
    fun getStoriesId(): List<Long>

}