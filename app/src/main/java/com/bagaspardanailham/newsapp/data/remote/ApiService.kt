package com.bagaspardanailham.newsapp.data.remote

import com.bagaspardanailham.newsapp.BuildConfig
import com.bagaspardanailham.newsapp.data.remote.responses.NewsResponse
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    @Headers("X-Api-Key: ${BuildConfig.API_KEY}")
    suspend fun getTopHeadlineNews(
        @Query("country") country: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): NewsResponse

    @GET("everything")
    @Headers("X-Api-Key: ${BuildConfig.API_KEY}")
    suspend fun getNews(
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("domains") domains: String
    ): NewsResponse

}