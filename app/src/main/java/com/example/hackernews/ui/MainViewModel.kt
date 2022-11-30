package com.example.hackernews.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackernews.data.local.StoriesEntity
import com.example.hackernews.data.model.StoriesResponse
import com.example.hackernews.repository.StoriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val storiesRepository: StoriesRepository
): ViewModel() {

    companion object{
        private const val TAG = "MainViewModel"
    }


    init {
//        getNewStories()
    }


    fun getNewStories(){
        viewModelScope.launch(coroutineExceptionHandler) {
            val response = storiesRepository.getNewStories()
            if (response.isSuccessful){
                Log.d(TAG, "getNewStories: ${response.body()}")
                response.body()?.forEach {
                    getStory(it)
                }
            }

        }
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
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
                            url = it.url
                        ))
                    }

                }

            }

        }
    }

}