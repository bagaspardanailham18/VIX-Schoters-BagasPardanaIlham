package com.bagaspardanailham.newsapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bagaspardanailham.newsapp.data.local.model.BookmarkNewsEntity
import com.bagaspardanailham.newsapp.data.local.model.NewsEntity
import com.bagaspardanailham.newsapp.data.local.model.TopHeadlineNewsEntity

@Database(
    entities = [TopHeadlineNewsEntity::class, NewsEntity::class, BookmarkNewsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase: RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile
        private var INSTANCE: NewsDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): NewsDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java, "news_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }

}