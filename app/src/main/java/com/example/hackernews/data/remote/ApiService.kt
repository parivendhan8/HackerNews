package com.example.hackernews.data.remote

import com.example.hackernews.data.model.StoriesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {


    @GET("v0/newstories.json?print=pretty")
    suspend fun getNewStories() : Response<HashSet<Long>>

    @GET("v0/item/{id}.json?print=pretty")
    suspend fun getStory(@Path("id") id: Long) : Response<StoriesResponse>


}