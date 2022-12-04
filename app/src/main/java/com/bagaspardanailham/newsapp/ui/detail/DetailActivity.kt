package com.bagaspardanailham.newsapp.ui.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction
import androidx.lifecycle.lifecycleScope
import com.bagaspardanailham.newsapp.R
import com.bagaspardanailham.newsapp.data.local.model.BookmarkNewsEntity
import com.bagaspardanailham.newsapp.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    //private val args: DetailActivityArgs = DetailActivityArgs.fromBundle(intent.extras as Bundle)

    private val detailViewModel by viewModels<DetailViewModel>()

    //private var id: Int = 0
    private lateinit var title: String
    private lateinit var description: String
    private lateinit var urlToImg: String
    private lateinit var publishedAt: String
    private lateinit var content: String
    private lateinit var author: String
    private lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //id = intent.extras?.get(EXTRA_ID) as Int
        title = intent.extras?.get(EXTRA_TITLE).toString().trim()
        description = intent.extras?.get(EXTRA_DESC).toString().trim()
        content = intent.extras?.get(EXTRA_CONTENT).toString().trim()
        urlToImg = intent.extras?.get(EXTRA_IMG).toString().trim()
        publishedAt = intent.extras?.get(EXTRA_DATE).toString().trim()
        author = intent.extras?.get(EXTRA_AUTHOR).toString().trim()
        url = intent.extras?.get(EXTRA_URL).toString().trim()

        isBookmarkedCheck()
        setContent()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setWindow()
        }
        //setAction()
    }

    private fun isBookmarkedCheck() {
        detailViewModel.getBookmarkedNewsById(title).observe(this@DetailActivity) { result ->
            if (result != null) {
                setBookmarkState(true)
                binding.btnToggleBookmark.setOnClickListener {
                    lifecycleScope.launch(Dispatchers.IO) {
                        detailViewModel.deleteBookmarkedNews(title)
                    }
                }
            } else {
                setBookmarkState(false)
                val news = BookmarkNewsEntity(
                    title = title, description = description, urlToImg = urlToImg, publishedAt = publishedAt, content = content, author = author, url = url
                )
                binding.btnToggleBookmark.setOnClickListener {
                    lifecycleScope.launch(Dispatchers.IO) {
                        detailViewModel.insertBookmarkedNews(news)
                    }
                }
            }
        }
    }

//    private fun setAction() {
//        val news = BookmarkNewsEntity(
//            title = title, description = description, urlToImg = urlToImg, publishedAt = publishedAt, content = content, author = author, url = url
//        )
//        binding.btnToggleBookmark.setOnClickListener {
//            if (isBookmark) {
//                lifecycleScope.launch(Dispatchers.IO) {
//                    detailViewModel.deleteBookmarkedNews(title)
//                }
//            } else {
//                lifecycleScope.launch(Dispatchers.IO) {
//                    detailViewModel.insertBookmarkedNews(news)
//                }
//            }
//        }
//    }

    private fun setContent() {
        binding.apply {
            tvTitleDetail.text = title
            tvDateDetail.text = publishedAt
            tvDescDetail.text = description

            Glide.with(this@DetailActivity)
                .load(urlToImg)
                .into(tvImgDetail)

        }
    }

    private fun setBookmarkState(state: Boolean) {
        val fab = binding.btnToggleBookmark
        if (state) {
            fab.isChecked = true
        } else {
            fab.isChecked = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setWindow() {
        val window: Window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val wic = window.decorView.windowInsetsController
            wic?.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
        } else
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.setDecorFitsSystemWindows(false)
            } else {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }

        setSupportActionBar(binding.customToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_DESC = "extra_desc"
        const val EXTRA_CONTENT = "extra_content"
        const val EXTRA_AUTHOR = "extra_author"
        const val EXTRA_IMG = "extra_img"
        const val EXTRA_DATE = "extra_date"
        const val EXTRA_URL = "extra_url"
    }
}