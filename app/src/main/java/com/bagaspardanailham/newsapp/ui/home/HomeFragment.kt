package com.bagaspardanailham.newsapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bagaspardanailham.newsapp.data.remote.Result
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagaspardanailham.newsapp.data.local.model.NewsEntity
import com.bagaspardanailham.newsapp.data.local.model.TopHeadlineNewsEntity
import com.bagaspardanailham.newsapp.data.remote.responses.ArticlesItem
import com.bagaspardanailham.newsapp.databinding.FragmentHomeBinding
import com.bagaspardanailham.newsapp.ui.detail.DetailActivity
import com.bagaspardanailham.newsapp.ui.detail.DetailActivity.Companion.EXTRA_AUTHOR
import com.bagaspardanailham.newsapp.ui.detail.DetailActivity.Companion.EXTRA_CONTENT
import com.bagaspardanailham.newsapp.ui.detail.DetailActivity.Companion.EXTRA_DATE
import com.bagaspardanailham.newsapp.ui.detail.DetailActivity.Companion.EXTRA_DESC
import com.bagaspardanailham.newsapp.ui.detail.DetailActivity.Companion.EXTRA_ID
import com.bagaspardanailham.newsapp.ui.detail.DetailActivity.Companion.EXTRA_IMG
import com.bagaspardanailham.newsapp.ui.detail.DetailActivity.Companion.EXTRA_TITLE
import com.bagaspardanailham.newsapp.ui.detail.DetailActivity.Companion.EXTRA_URL
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel>()
    private lateinit var topNewsAdapter: ListTopHeadlineNewsAdapter
    private lateinit var newsAdapter: ListNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topNewsAdapter = ListTopHeadlineNewsAdapter()
        newsAdapter = ListNewsAdapter()

        setTopHeadlineNewsData()
        setNewsData()
    }

    private fun setTopHeadlineNewsData() {
        lifecycleScope.launch {
            homeViewModel.getTopHeadlineNews().observe(viewLifecycleOwner) { response ->
                Log.d("home", response.toString())
                when (response) {
                    is Result.Success -> {
                        setTopHeadlineNewsRv(response.data)
                        binding.headlineProgressBar.visibility = View.GONE
                    }
                    is Result.Loading -> {
                        binding.headlineProgressBar.visibility = View.VISIBLE
                    }
                    is Result.Error -> {
                        binding.headlineProgressBar.visibility = View.GONE
                        Toast.makeText(requireActivity(), "Bad Internet Connection", Toast.LENGTH_SHORT).show()
                        Log.e("error", response.error)
                    }
                }
            }
        }
    }

    private fun setNewsData() {
        lifecycleScope.launch {
            homeViewModel.getNews().observe(viewLifecycleOwner) { response ->
                Log.d("home", response.toString())
                when (response) {
                    is Result.Success -> {
                        setNewsRv(response.data)
                        binding.newsProgressBar.visibility = View.GONE
                    }
                    is Result.Loading -> {
                        binding.newsProgressBar.visibility = View.VISIBLE
                    }
                    is Result.Error -> {
                        binding.newsProgressBar.visibility = View.GONE
                        Toast.makeText(requireActivity(), "Bad Internet Connection", Toast.LENGTH_SHORT).show()
                        Log.e("error", response.error)
                    }
                }
            }
        }
    }

    private fun setTopHeadlineNewsRv(data: List<TopHeadlineNewsEntity?>?) {
        topNewsAdapter.submitList(data)
        binding.rvTopNews.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvTopNews.adapter = topNewsAdapter
        binding.rvTopNews.setHasFixedSize(true)

        topNewsAdapter.setOnItemClickCallback(object : ListTopHeadlineNewsAdapter.OnItemClickCallback {
            override fun onItemClicked(data: TopHeadlineNewsEntity) {
//                val intent = HomeFragmentDirections.actionNavigationHomeToDetailActivity()
//                intent.title = data.title.toString().trim()
//                intent.description = data.description.toString().trim()
//                intent.imageUrl = data.urlToImage.toString().trim()
//                intent.publishedAt = data.publishedAt.toString().trim()
//                view?.findNavController()?.navigate(intent)

                val intent = Intent(requireActivity(), DetailActivity::class.java)
                val bundle = Bundle()
                //bundle.putInt(EXTRA_ID, data.id)
                bundle.putString(EXTRA_TITLE, data.title)
                bundle.putString(EXTRA_DESC, data.description)
                bundle.putString(EXTRA_CONTENT, data.content)
                bundle.putString(EXTRA_DATE, data.publishedAt)
                bundle.putString(EXTRA_IMG, data.urlToImg)
                bundle.putString(EXTRA_AUTHOR, data.author)
                bundle.putString(EXTRA_URL, data.url)

                intent.putExtras(bundle)
                startActivity(intent)
            }

        })
    }

    private fun setNewsRv(data: List<NewsEntity?>?) {
        newsAdapter.submitList(data)
        binding.rvNews.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvNews.adapter = newsAdapter
        binding.rvNews.setHasFixedSize(true)

        newsAdapter.setOnItemClickCallback(object : ListNewsAdapter.OnItemClickCallback {
            override fun onItemClicked(data: NewsEntity) {
//                val intent = HomeFragmentDirections.actionNavigationHomeToDetailActivity()
//                intent.title = data.title.toString().trim()
//                intent.description = data.description.toString().trim()
//                intent.imageUrl = data.urlToImage.toString().trim()
//                intent.publishedAt = data.publishedAt.toString().trim()
//                view?.findNavController()?.navigate(intent)
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                val bundle = Bundle()
                bundle.putString(EXTRA_TITLE, data.title)
                bundle.putString(EXTRA_DESC, data.description)
                bundle.putString(EXTRA_DATE, data.publishedAt)
                bundle.putString(EXTRA_IMG, data.urlToImg)

                intent.putExtras(bundle)
                startActivity(intent)
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}