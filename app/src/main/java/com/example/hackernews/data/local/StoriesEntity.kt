package com.example.hackernews.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["storyId"], unique = true)]
)
data class StoriesEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val storyId: Long?,
    val by: String?,
    val descendants: Long?,
    val score: Long?,
    val time: Long?,
    val title: String?,
    val type: String?,
    val url: String?
)

