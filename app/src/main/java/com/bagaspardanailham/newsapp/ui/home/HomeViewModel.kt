package com.bagaspardanailham.newsapp.ui.home

import androidx.lifecycle.*
import com.bagaspardanailham.newsapp.data.remote.Result
import com.bagaspardanailham.newsapp.data.NewsRepository
import com.bagaspardanailham.newsapp.data.local.model.NewsEntity
import com.bagaspardanailham.newsapp.data.local.model.TopHeadlineNewsEntity
import com.bagaspardanailham.newsapp.data.remote.responses.NewsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    fun getTopHeadlineNews(): LiveData<Result<List<TopHeadlineNewsEntity>>> = newsRepository.getTopHeadlineNews()

    fun getNews(): LiveData<Result<List<NewsEntity>>> = newsRepository.getNews()

}