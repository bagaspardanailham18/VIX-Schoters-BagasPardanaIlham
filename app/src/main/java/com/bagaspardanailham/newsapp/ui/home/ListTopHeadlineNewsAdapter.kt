package com.bagaspardanailham.newsapp.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bagaspardanailham.newsapp.data.local.model.TopHeadlineNewsEntity
import com.bagaspardanailham.newsapp.data.remote.responses.ArticlesItem
import com.bagaspardanailham.newsapp.databinding.ItemRowTopNewsBinding
import com.bagaspardanailham.newsapp.ui.detail.DetailActivity
import com.bagaspardanailham.newsapp.ui.home.ListNewsAdapter.Companion.DIFF_CALLBACK
import com.bumptech.glide.Glide

class ListTopHeadlineNewsAdapter : ListAdapter<TopHeadlineNewsEntity, ListTopHeadlineNewsAdapter.ListTopHeadlineNewsVH>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

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
        fun bind(topNews: TopHeadlineNewsEntity) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(topNews.urlToImg)
                    .into(tvImgItem)

                tvTitleItem.text = topNews.title.toString().trim()
                tvDateCreatedItem.text = topNews.publishedAt.toString().trim()

                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(topNews)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: TopHeadlineNewsEntity)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<TopHeadlineNewsEntity> =
            object : DiffUtil.ItemCallback<TopHeadlineNewsEntity>() {
                override fun areItemsTheSame(
                    oldItem: TopHeadlineNewsEntity,
                    newItem: TopHeadlineNewsEntity
                ): Boolean {
                    return oldItem.title == newItem.title
                }

                override fun areContentsTheSame(
                    oldItem: TopHeadlineNewsEntity,
                    newItem: TopHeadlineNewsEntity
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }
}