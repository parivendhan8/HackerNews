package com.example.hackernews.ui.adapter

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernews.data.local.StoriesEntity
import com.example.hackernews.databinding.ChildStoriesBinding

class StoriesAdapter: ListAdapter<StoriesEntity, StoriesAdapter.MyViewHolder>(DiffCallback()) {

    class DiffCallback: DiffUtil.ItemCallback<StoriesEntity>(){
        override fun areItemsTheSame(oldItem: StoriesEntity, newItem: StoriesEntity)
        = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: StoriesEntity, newItem: StoriesEntity)
        = oldItem == newItem

    }

    inner class MyViewHolder(val binding: ChildStoriesBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(ChildStoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {

            val data = getItem(position)

            tvName.text = "By: ${data?.by}"
            tvTitle.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(data?.title, Html.FROM_HTML_MODE_COMPACT)
            }else{
                Html.fromHtml(data?.title)
            }
            tvScore.text = data?.score.toString()
        }
    }
}