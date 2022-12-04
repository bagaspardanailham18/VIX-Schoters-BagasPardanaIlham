package com.bagaspardanailham.newsapp.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bagaspardanailham.newsapp.data.NewsRepository
import com.bagaspardanailham.newsapp.data.local.model.BookmarkNewsEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    fun getBookmarkedNews(): LiveData<List<BookmarkNewsEntity>> =
        newsRepository.getBookmarkedNews()
}