package com.example.hackernews.repository

import com.example.hackernews.data.local.AppDataBase
import com.example.hackernews.data.local.StoriesEntity
import com.example.hackernews.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class StoriesRepository @Inject constructor(
    private val apiService: ApiService,
    private val appDataBase: AppDataBase,
) {


    suspend fun getNewStories() = apiService.getNewStories()

    suspend fun getNewStories(storyId: Long) = flow {
        val response = apiService.getStory(storyId)
        emit(response)
    }.flowOn(Dispatchers.IO)


    suspend fun addStories(storiesEntity: StoriesEntity){
        appDataBase.storiesDao().addStroires(storiesEntity)
    }



}