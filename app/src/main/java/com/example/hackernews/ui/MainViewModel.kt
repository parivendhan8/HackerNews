package com.example.hackernews.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.hackernews.data.local.StoriesEntity
import com.example.hackernews.repository.StoriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val storiesRepository: StoriesRepository
): ViewModel() {

    companion object{
        private const val TAG = "MainViewModel"
    }


    private var job: Job? = null

    private val _isResponse = MutableLiveData<Boolean>()
    val isResponse: LiveData<Boolean> = _isResponse

    val searchValue = MutableLiveData<List<StoriesEntity>>()
    val storiesLiveData = storiesRepository.getStories()


    fun doSearch(value: String){

        viewModelScope.launch (Dispatchers.IO) {
            val list = storiesRepository.doSearch("%$value%")
            searchValue.postValue(list)

        }
    }



    fun getNewStories(){

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {

            job?.cancel()

            val response = storiesRepository.getNewStories()

            if (response.isSuccessful){
                _isResponse.postValue(true)
                Log.d(TAG, "getNewStories: ${response.body()}")

                val responseList = response.body() ?: HashSet()

                storiesRepository.getStoriesId().forEach {
                    if (responseList.contains(it)){
                        responseList.remove(it)
                    }
                }

                job = launch {
                    responseList.forEach {
                        getStory(it)
                    }
                }

            }else{
                _isResponse.postValue(false)
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