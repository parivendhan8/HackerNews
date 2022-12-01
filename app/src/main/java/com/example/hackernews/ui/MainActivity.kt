package com.example.hackernews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.hackernews.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()

    companion object{
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.tvText.setOnClickListener {

            viewModel.apply {
                viewModel.getNewStories()
            }


        }

        binding.tvText2.setOnClickListener {

            viewModel.apply {
                cancel()
            }


        }


        viewModel.stories.observe(this){
            binding.tvText.text = it?.size?.toString() ?: "TEST"
        }

       /* lifecycleScope.launchWhenStarted {
            viewModel.allStories.collect{
                binding.tvText.text = it.size.toString()
            }
        }*/

        
        


    }
}