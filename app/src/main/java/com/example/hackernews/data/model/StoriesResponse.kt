package com.example.hackernews.data.model

data class StoriesResponse (
    val by: String,
    val descendants: Long,
    val id: Long,
    val score: Long,
    val time: Long,
    val title: String,
    val type: String,
    val url: String
)
