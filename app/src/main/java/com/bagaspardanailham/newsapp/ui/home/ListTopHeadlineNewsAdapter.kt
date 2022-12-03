package com.bagaspardanailham.newsapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bagaspardanailham.newsapp.data.remote.responses.ArticlesItem
import com.bagaspardanailham.newsapp.databinding.ItemRowTopNewsBinding
import com.bumptech.glide.Glide

class ListTopHeadlineNewsAdapter : ListAdapter<ArticlesItem, ListTopHeadlineNewsAdapter.ListTopHeadlineNewsVH>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListTopHeadlineNewsVH {
        val binding = ItemRowTopNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListTopHeadlineNewsVH(binding)
    }

    override fun onBindViewHolder(holder: ListTopHeadlineNewsVH, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class ListTopHeadlineNewsVH(private val binding: ItemRowTopNewsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(topNews: ArticlesItem) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(topNews.urlToImage)
                    .into(tvImgItem)

                tvTitleItem.text = topNews.title.toString().trim()
                tvDateCreatedItem.text = topNews.publishedAt.toString().trim()
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ArticlesItem> =
            object : DiffUtil.ItemCallback<ArticlesItem>() {
                override fun areItemsTheSame(
                    oldItem: ArticlesItem,
                    newItem: ArticlesItem
                ): Boolean {
                    return oldItem.title == newItem.title
                }

                override fun areContentsTheSame(
                    oldItem: ArticlesItem,
                    newItem: ArticlesItem
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }
}