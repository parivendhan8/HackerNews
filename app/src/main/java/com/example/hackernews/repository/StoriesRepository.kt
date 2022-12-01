package com.example.hackernews.repository

import com.example.hackernews.data.local.AppDataBase
import com.example.hackernews.data.local.StoriesDao
import com.example.hackernews.data.local.StoriesEntity
import com.example.hackernews.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
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
    = storiesDao.addStroire(storiesEntity)

    fun getStories() = storiesDao.getStories()

    fun getStoriesId() = storiesDao.getStoriesId().toHashSet()



}