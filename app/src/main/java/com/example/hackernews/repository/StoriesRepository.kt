package com.example.hackernews.repository

import androidx.paging.*
import com.example.hackernews.data.local.StoriesDao
import com.example.hackernews.data.local.StoriesEntity
import com.example.hackernews.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class StoriesRepository @Inject constructor(
    private val apiService: ApiService,
    private val storiesDao: StoriesDao,
) {


    suspend fun getNewStories() = apiService.getNewStories()

    suspend fun getNewStories(storyId: Long) = flow {
        val response = apiService.getStory(storyId)
        emit(response)
    }.flowOn(Dispatchers.IO)


    fun addStories(storiesEntity: StoriesEntity)
    = storiesDao.addStories(storiesEntity)

    fun getStoriesId() = storiesDao.getStoriesId().toHashSet()

    fun getStories() = storiesDao.getStories()

    fun doSearch(value: String) = storiesDao.search(value)


}
