package com.example.hackernews.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.hackernews.databinding.ActivityMainBinding
import com.example.hackernews.ui.adapter.StoriesAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val storiesAdapter by lazy { StoriesAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initView()
        observer()


    }


    private fun initView() {

        binding.apply {
            swipe.setOnRefreshListener {
                viewModel.getNewStories()
                swipe.isRefreshing = false
            }
        }


        viewModel.apply {

            getNewStories()

            binding.recyclerView.adapter = storiesAdapter

            binding.apply {
                etSearch.doAfterTextChanged {
                    if (etSearch.text?.length!! > 0)
                        doSearch(etSearch.text.toString())
                    else
                        addStoriesObserver()
                }
            }


        }

    }

    private fun observer() {
        viewModel.apply {

            addStoriesObserver()

            searchValue.observe(this@MainActivity) {
                storiesLiveData.removeObservers(this@MainActivity);
                storiesAdapter.submitList(it)
            }

            isResponse.observe(this@MainActivity){
                binding.swipe.isRefreshing = false
            }

        }
    }

    private fun addStoriesObserver() {
        viewModel.storiesLiveData.observe(this) {
            storiesAdapter.submitList(it)
        }
    }
}