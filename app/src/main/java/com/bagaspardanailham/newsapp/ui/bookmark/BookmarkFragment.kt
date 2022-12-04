package com.bagaspardanailham.newsapp.ui.bookmark

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagaspardanailham.newsapp.data.local.model.BookmarkNewsEntity
import com.bagaspardanailham.newsapp.databinding.FragmentBookmarkBinding
import com.bagaspardanailham.newsapp.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val bookmarkViewModel by viewModels<BookmarkViewModel>()
    private lateinit var adapter: ListBookmarkedNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ListBookmarkedNewsAdapter()
        setBookmarkedNewsData()
    }

    private fun setBookmarkedNewsData() {
        lifecycleScope.launch {
            bookmarkViewModel.getBookmarkedNews().observe(viewLifecycleOwner) { data ->
                if (data.isNotEmpty()) {
                    setBookmarkedNewsRv(data)
                } else {
                    showEmpty()
                }
            }
        }
    }

    private fun setBookmarkedNewsRv(news: List<BookmarkNewsEntity>) {
        binding.rvBookmarkedNews.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvBookmarkedNews.adapter = adapter
        adapter.submitList(news)
        binding.rvBookmarkedNews.setHasFixedSize(true)
        binding.lottieEmpty.visibility = View.GONE

        adapter.setOnItemClickCallback(object : ListBookmarkedNewsAdapter.OnItemClickCallback {
            override fun onItemClicked(data: BookmarkNewsEntity) {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                val bundle = Bundle()
                bundle.putString(DetailActivity.EXTRA_TITLE, data.title)
                bundle.putString(DetailActivity.EXTRA_DESC, data.description)
                bundle.putString(DetailActivity.EXTRA_DATE, data.publishedAt)
                bundle.putString(DetailActivity.EXTRA_IMG, data.urlToImg)

                intent.putExtras(bundle)
                startActivity(intent)
            }

        })
    }

    private fun showEmpty() {
        binding.lottieEmpty.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}