package com.sekhgmainuddin.assignmentmoengage.presentation.main.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sekhgmainuddin.assignmentmoengage.R
import com.sekhgmainuddin.assignmentmoengage.data.dto.Article
import com.sekhgmainuddin.assignmentmoengage.databinding.FragmentBookmarkedBinding
import com.sekhgmainuddin.assignmentmoengage.presentation.base.BaseFragment
import com.sekhgmainuddin.assignmentmoengage.presentation.main.MainViewModel
import com.sekhgmainuddin.assignmentmoengage.presentation.main.adapters.NewsAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BookmarkedFragment : BaseFragment() {

    private var _binding: FragmentBookmarkedBinding? = null
    private val binding: FragmentBookmarkedBinding
        get() = _binding!!
    private lateinit var newsAdapter: NewsAdapter
    private val viewModel by activityViewModels<MainViewModel>()
    private val list = ArrayList<Article>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_bookmarked, container, false
        )
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        registerAdapters()
        bindObservers()
    }

    private fun registerAdapters() {
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
                    showToast("Bookmarked News Deleted")
                } else {
                    viewModel.addBookMark(item)
                    showToast("News Saved")
                }
                item.isBookmarked = !item.isBookmarked
            },
        )
        binding.bookmarkedRV.adapter = newsAdapter
    }

    private fun bindObservers() {
        viewLifecycleOwner.lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.savedNewsList.collect{
                    list.clear()
                    if (it != null) {
                        list.addAll(it)
                    }
                    newsAdapter.submitList(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}