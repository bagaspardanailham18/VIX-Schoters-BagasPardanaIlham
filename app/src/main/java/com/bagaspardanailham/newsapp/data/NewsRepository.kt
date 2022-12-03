package com.bagaspardanailham.newsapp.data

import android.os.Build
import com.bagaspardanailham.newsapp.data.remote.Result
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bagaspardanailham.newsapp.BuildConfig
import com.bagaspardanailham.newsapp.data.remote.ApiService
import com.bagaspardanailham.newsapp.data.remote.responses.NewsResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(private val apiService: ApiService) {

    fun getTopHeadlineNews(): LiveData<Result<NewsResponse>> = liveData {
        try {
            val response = apiService.getTopHeadlineNews("id", 1, 5)
            emit(Result.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.toString()))
        }
    }

    fun getNews(): LiveData<Result<NewsResponse>> = liveData {
        try {
            val response = apiService.getNews("id", 1, 5, "detik.com")
            emit(Result.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.toString()))
        }
    }

}