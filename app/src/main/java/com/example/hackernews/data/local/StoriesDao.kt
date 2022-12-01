package com.example.hackernews.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StoriesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addStroire(storiesEntity: StoriesEntity)

    @Query("SELECT * FROM StoriesEntity")
    fun getStories(): LiveData<List<StoriesEntity>>

    @Query("SELECT storyId FROM StoriesEntity")
    fun getStoriesId(): List<Long>

}