package com.bagaspardanailham.newsapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bagaspardanailham.newsapp.data.NewsRepository
import com.bagaspardanailham.newsapp.data.local.model.BookmarkNewsEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val newsRepository: NewsRepository): ViewModel() {

    fun getBookmarkedNewsById(title: String) = newsRepository.getBookmarkedNewsByTitle(title)

    suspend fun insertBookmarkedNews(news: BookmarkNewsEntity) {
        newsRepository.insertBookmarkedNews(news)
    }

    suspend fun deleteBookmarkedNews(title: String) {
        newsRepository.deleteBookmarkedNews(title)
    }

}