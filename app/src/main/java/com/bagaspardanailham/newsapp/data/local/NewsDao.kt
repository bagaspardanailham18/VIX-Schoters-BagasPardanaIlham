package com.bagaspardanailham.newsapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bagaspardanailham.newsapp.data.local.model.BookmarkNewsEntity
import com.bagaspardanailham.newsapp.data.local.model.NewsEntity
import com.bagaspardanailham.newsapp.data.local.model.TopHeadlineNewsEntity

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllNews(news: List<NewsEntity?>?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllTopHeadlineNews(news: List<TopHeadlineNewsEntity?>?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBookmarkNews(news: BookmarkNewsEntity)

    @Query("SELECT * FROM top_headline_news")
    fun getAllTopHeadlineNews(): LiveData<List<TopHeadlineNewsEntity>>

    @Query("SELECT * FROM news")
    fun getAllNews(): LiveData<List<NewsEntity>>

    @Query("SELECT * FROM bookmarked_news")
    fun getAllBookmarkedNews(): LiveData<List<BookmarkNewsEntity>>

    @Query("SELECT * FROM bookmarked_news WHERE title=:title")
    fun getBookmarkedNewsByTitle(title: String): LiveData<BookmarkNewsEntity>

    @Query("DELETE FROM top_headline_news")
    suspend fun deleteAllTopHeadlineNews()

    @Query("DELETE FROM news")
    suspend fun deleteAllNews()

    @Query("DELETE FROM bookmarked_news WHERE title=:title")
    suspend fun deleteBookmarkedNews(title: String)

}