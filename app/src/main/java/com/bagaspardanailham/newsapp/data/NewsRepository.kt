package com.bagaspardanailham.newsapp.data

import android.os.Build
import com.bagaspardanailham.newsapp.data.remote.Result
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.bagaspardanailham.newsapp.data.local.NewsDatabase
import com.bagaspardanailham.newsapp.data.local.model.BookmarkNewsEntity
import com.bagaspardanailham.newsapp.data.local.model.NewsEntity
import com.bagaspardanailham.newsapp.data.local.model.TopHeadlineNewsEntity
import com.bagaspardanailham.newsapp.data.remote.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(private val apiService: ApiService, private val newsDatabase: NewsDatabase) {

    fun getTopHeadlineNews(): LiveData<Result<List<TopHeadlineNewsEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getTopHeadlineNews("id", 1, 5).articles
            val topHeadlineList = response?.map {
                TopHeadlineNewsEntity(
                    title = it?.title,
                    description = it?.description,
                    urlToImg = it?.urlToImage,
                    publishedAt = it?.publishedAt,
                    content = it?.content,
                    author = it?.author,
                    url = it?.url
                )
            }
            newsDatabase.newsDao().deleteAllTopHeadlineNews()
            newsDatabase.newsDao().insertAllTopHeadlineNews(topHeadlineList)
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<TopHeadlineNewsEntity>>> = newsDatabase.newsDao().getAllTopHeadlineNews().map {
            Result.Success(it)
        }
        emitSource(localData)
    }

    fun getNews(): LiveData<Result<List<NewsEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getNews("id", 1, 5, "detik.com").articles
            val newsList = response?.map {
                NewsEntity(
                    title = it?.title,
                    description = it?.description,
                    urlToImg = it?.urlToImage,
                    publishedAt = it?.publishedAt,
                    content = it?.content,
                    author = it?.author,
                    url = it?.url
                )
            }
            newsDatabase.newsDao().deleteAllNews()
            newsDatabase.newsDao().insertAllNews(newsList)
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }

        val localData: LiveData<Result<List<NewsEntity>>> = newsDatabase.newsDao().getAllNews().map {
            Result.Success(it)
        }
        emitSource(localData)
    }

    fun getBookmarkedNews(): LiveData<List<BookmarkNewsEntity>> {
        return newsDatabase.newsDao().getAllBookmarkedNews()
    }

    fun getBookmarkedNewsByTitle(title: String): LiveData<BookmarkNewsEntity> {
        return newsDatabase.newsDao().getBookmarkedNewsByTitle(title)
    }

    suspend fun insertBookmarkedNews(news: BookmarkNewsEntity) {
        newsDatabase.newsDao().insertBookmarkNews(news)
    }

    suspend fun deleteBookmarkedNews(title: String) {
        newsDatabase.newsDao().deleteBookmarkedNews(title)
    }

}