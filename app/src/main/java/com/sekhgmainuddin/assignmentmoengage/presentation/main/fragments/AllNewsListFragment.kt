package com.sekhgmainuddin.assignmentmoengage.presentation.main.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sekhgmainuddin.assignmentmoengage.R
import com.sekhgmainuddin.assignmentmoengage.data.dto.Article
import com.sekhgmainuddin.assignmentmoengage.databinding.FragmentAllNewsListBinding
import com.sekhgmainuddin.assignmentmoengage.presentation.base.BaseFragment
import com.sekhgmainuddin.assignmentmoengage.presentation.main.MainViewModel
import com.sekhgmainuddin.assignmentmoengage.presentation.main.adapters.NewsAdapter
import com.sekhgmainuddin.assignmentmoengage.presentation.main.uiStates.GetNewsState
import kotlinx.coroutines.launch

class AllNewsListFragment : BaseFragment() {

    private var _binding: FragmentAllNewsListBinding? = null
    private val binding: FragmentAllNewsListBinding
        get() = _binding!!
    private val viewModel by activityViewModels<MainViewModel>()
    private lateinit var newsAdapter: NewsAdapter
    private val list = ArrayList<Article>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_all_news_list, container, false
        )
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerAdapters()
        bindObserver()
    }

    private fun registerAdapters() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        newsAdapter = NewsAdapter(
            onNewsClicked = {
                val customIntent = CustomTabsIntent.Builder()
                val customTabsIntent = customIntent.build()
                customTabsIntent.intent.setPackage("com.android.chrome")
                customTabsIntent.launchUrl(requireActivity(), Uri.parse(list[it].url))
            },
            onBookmarkAddOrRemove = {
                val item = list[it]
                if (item.isBookmarked) {
                    viewModel.deleteBookMark(item)
                    showToast("News Deleted")
                } else {
                    viewModel.addBookMark(item)
                    showToast("News added to Bookmark")
                }
                item.isBookmarked = !item.isBookmarked
                newsAdapter.notifyItemChanged(it)
            },
        )
        binding.newsRV.adapter = newsAdapter
        binding.retryButton.setOnClickListener {
            viewModel.getNews()
        }
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.sortASC -> {
                    viewModel.sortAsc()
                }

                R.id.sortDSC -> {
                    viewModel.sortDsc()
                }
            }
            true
        }
    }

    private fun bindObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getNewsState.collect {
                    when (it) {
                        GetNewsState.Initial -> {}
                        is GetNewsState.Loaded -> {
                            list.clear()
                            list.addAll(it.newsDto.articles)
                            // Added this because ListAdapter doesn't even calls the diffutil functions if the list is same(instance wise not value wise)
                            newsAdapter.submitList(it.newsDto.articles.toList())
                        }

                        GetNewsState.Loading -> {}
                        is GetNewsState.Error -> {
                            showToast(it.message)
                        }

                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}