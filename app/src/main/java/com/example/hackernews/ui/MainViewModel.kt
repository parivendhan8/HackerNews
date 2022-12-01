package com.example.hackernews.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackernews.data.local.StoriesEntity
import com.example.hackernews.repository.StoriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val storiesRepository: StoriesRepository
): ViewModel() {

    private var job: Job? = null
    val stories = storiesRepository.getStories()
    private val successIds = arrayListOf<Long>()

    companion object{
        private const val TAG = "MainViewModel"
    }



    fun cancel(){
        job?.cancel()
    }


    fun getNewStories(){

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {

            val response = storiesRepository.getNewStories()

            if (response.isSuccessful){

                Log.d(TAG, "getNewStories: ${response.body()}")

                val responseList = response.body() ?: HashSet()

                storiesRepository.getStoriesId().forEach {
                    if (responseList.contains(it)){
                        responseList.remove(it)
                    }
                }

                job = launch {
                    responseList.forEach {
                        delay(1000)
                        getStory(it)
                    }
                }

            }

        }
    }



    private fun getStory(id: Long){
       viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {

            val response = storiesRepository.getNewStories(id)
            response.catch {

                Log.e(TAG, "getStory: ERROR", )

            }.collect { it ->

                Log.d(TAG, "getStory: ${it.body()}")

                if (it.isSuccessful){
                    it.body()?.let {
                        storiesRepository.addStories(StoriesEntity(
                            by = it.by,
                            descendants = it.descendants,
                            score = it.score,
                            time = it.time,
                            title = it.title,
                            type = it.type,
                            url = it.url,
                            storyId = it.id
                        ))
                    }

                }

            }

        }
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
    }


}