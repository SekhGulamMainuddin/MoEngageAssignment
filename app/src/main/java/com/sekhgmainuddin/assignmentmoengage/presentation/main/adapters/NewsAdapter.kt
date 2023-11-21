package com.sekhgmainuddin.assignmentmoengage.presentation.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sekhgmainuddin.assignmentmoengage.R
import com.sekhgmainuddin.assignmentmoengage.data.dto.Article
import com.sekhgmainuddin.assignmentmoengage.databinding.NewsItemLayoutBinding
import java.text.SimpleDateFormat

// RecyclerView adapter to show News Articles
class NewsAdapter(val onNewsClicked: (Int) -> Unit, val onBookmarkAddOrRemove: (Int) -> Unit) :
    ListAdapter<Article, NewsAdapter.NewsViewHolder>(NewsDiffCallback()) {

    private class NewsDiffCallback() : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    inner class NewsViewHolder(private val binding: NewsItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.newsCV.setOnClickListener {
                onNewsClicked(adapterPosition)
            }
            binding.addOrRemoveFromBookMark.setOnClickListener {
                onBookmarkAddOrRemove(adapterPosition)
            }
        }

        fun bind(data: Article) {
            binding.data = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.news_item_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}