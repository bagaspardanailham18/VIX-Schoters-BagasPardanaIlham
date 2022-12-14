package com.bagaspardanailham.newsapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bagaspardanailham.newsapp.data.local.model.NewsEntity
import com.bagaspardanailham.newsapp.data.remote.responses.ArticlesItem
import com.bagaspardanailham.newsapp.databinding.ItemRowNewsBinding
import com.bagaspardanailham.newsapp.ui.home.ListTopHeadlineNewsAdapter.Companion.DIFF_CALLBACK
import com.bumptech.glide.Glide

class ListNewsAdapter : ListAdapter<NewsEntity, ListNewsAdapter.ListNewsVH>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

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
        fun bind(news: NewsEntity) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(news.urlToImg)
                    .into(tvImgItem)

                tvTitleItem.text = news.title.toString().trim()
//                tvDescItem.text = news.description.toString().trim()
                tvDateCreatedItem.text = news.publishedAt.toString().trim()

                itemView.setOnClickListener { onItemClickCallback.onItemClicked(news) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: NewsEntity)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<NewsEntity> =
            object : DiffUtil.ItemCallback<NewsEntity>() {
                override fun areItemsTheSame(
                    oldItem: NewsEntity,
                    newItem: NewsEntity
                ): Boolean {
                    return oldItem.title == newItem.title
                }

                override fun areContentsTheSame(
                    oldItem: NewsEntity,
                    newItem: NewsEntity
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }
}