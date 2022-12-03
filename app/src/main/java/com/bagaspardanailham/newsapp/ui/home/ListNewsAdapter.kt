package com.bagaspardanailham.newsapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bagaspardanailham.newsapp.data.remote.responses.ArticlesItem
import com.bagaspardanailham.newsapp.databinding.ItemRowNewsBinding
import com.bumptech.glide.Glide

class ListNewsAdapter : ListAdapter<ArticlesItem, ListNewsAdapter.ListNewsVH>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNewsVH {
        val binding = ItemRowNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListNewsVH(binding)
    }

    override fun onBindViewHolder(holder: ListNewsVH, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class ListNewsVH(private val binding: ItemRowNewsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(news: ArticlesItem) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(news.urlToImage)
                    .into(tvImgItem)

                tvTitleItem.text = news.title.toString().trim()
//                tvDescItem.text = news.description.toString().trim()
                tvDateCreatedItem.text = news.publishedAt.toString().trim()
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