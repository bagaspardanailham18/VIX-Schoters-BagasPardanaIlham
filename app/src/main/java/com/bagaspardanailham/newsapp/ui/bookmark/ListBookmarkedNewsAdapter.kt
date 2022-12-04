package com.bagaspardanailham.newsapp.ui.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bagaspardanailham.newsapp.data.local.model.BookmarkNewsEntity
import com.bagaspardanailham.newsapp.databinding.ItemRowNewsBinding
import com.bumptech.glide.Glide

class ListBookmarkedNewsAdapter: ListAdapter<BookmarkNewsEntity, ListBookmarkedNewsAdapter.ListBookmarkedNewsVH>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListBookmarkedNewsVH {
        val binding = ItemRowNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListBookmarkedNewsVH(binding)
    }

    override fun onBindViewHolder(holder: ListBookmarkedNewsVH, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class ListBookmarkedNewsVH(private val binding: ItemRowNewsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(news: BookmarkNewsEntity) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(news.urlToImg)
                    .into(tvImgItem)

                tvTitleItem.text = news.title.toString().trim()
                tvDateCreatedItem.text = news.publishedAt.toString().trim()

                itemView.setOnClickListener { onItemClickCallback.onItemClicked(news) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: BookmarkNewsEntity)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<BookmarkNewsEntity> =
            object : DiffUtil.ItemCallback<BookmarkNewsEntity>() {
                override fun areItemsTheSame(
                    oldItem: BookmarkNewsEntity,
                    newItem: BookmarkNewsEntity
                ): Boolean {
                    return oldItem.title == newItem.title
                }

                override fun areContentsTheSame(
                    oldItem: BookmarkNewsEntity,
                    newItem: BookmarkNewsEntity
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }
}