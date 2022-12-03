package com.bagaspardanailham.newsapp.ui.home

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagaspardanailham.newsapp.data.remote.responses.ArticlesItem
import com.bagaspardanailham.newsapp.databinding.FragmentHomeBinding
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
                        setTopHeadlineNewsRv(response.data.articles)
                    }
                    is Result.Loading -> {

                    }
                    is Result.Error -> {
                        Toast.makeText(requireActivity(), response.error, Toast.LENGTH_SHORT).show()
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
                        setNewsRv(response.data.articles)
                    }
                    is Result.Loading -> {

                    }
                    is Result.Error -> {
                        Toast.makeText(requireActivity(), response.error, Toast.LENGTH_SHORT).show()
                        Log.e("error", response.error)
                    }
                }
            }
        }
    }

    private fun setTopHeadlineNewsRv(data: List<ArticlesItem?>?) {
        topNewsAdapter.submitList(data)
        binding.rvTopNews.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvTopNews.adapter = topNewsAdapter
        binding.rvTopNews.setHasFixedSize(true)
    }

    private fun setNewsRv(data: List<ArticlesItem?>?) {
        newsAdapter.submitList(data)
        binding.rvNews.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvNews.adapter = newsAdapter
        binding.rvNews.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}