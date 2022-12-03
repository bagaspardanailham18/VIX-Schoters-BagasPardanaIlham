package com.bagaspardanailham.newsapp.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bagaspardanailham.newsapp.data.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}