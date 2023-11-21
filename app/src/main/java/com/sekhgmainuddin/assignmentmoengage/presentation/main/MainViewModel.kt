package com.sekhgmainuddin.assignmentmoengage.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sekhgmainuddin.assignmentmoengage.data.db.entities.SavedNews
import com.sekhgmainuddin.assignmentmoengage.data.db.entities.toArticle
import com.sekhgmainuddin.assignmentmoengage.data.dto.Article
import com.sekhgmainuddin.assignmentmoengage.data.dto.NewsDto
import com.sekhgmainuddin.assignmentmoengage.domain.usecases.DeleteNewsUseCase
import com.sekhgmainuddin.assignmentmoengage.domain.usecases.GetAllSavedNewsUseCase
import com.sekhgmainuddin.assignmentmoengage.domain.usecases.GetNewsUseCase
import com.sekhgmainuddin.assignmentmoengage.domain.usecases.SaveNewsUseCase
import com.sekhgmainuddin.assignmentmoengage.helper.NetworkResult
import com.sekhgmainuddin.assignmentmoengage.presentation.main.uiStates.GetNewsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val getAllSavedNewsUseCase: GetAllSavedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val deleteNewsUseCase: DeleteNewsUseCase
) : ViewModel() {

    init {
        getAllSavedNews()
    }

    private var _getNewsState = MutableStateFlow<GetNewsState>(GetNewsState.Initial)
    val getNewsState: StateFlow<GetNewsState>
        get() = _getNewsState

    private var newsDto: NewsDto? = null

    fun getNews() = viewModelScope.launch(Dispatchers.IO) {
        getNewsUseCase().collect {
            when (it) {
                is NetworkResult.Success -> {
                    newsDto = it.data
                    _getNewsState.value = GetNewsState.Loaded(it.data!!)
                    Log.d("DataReceived", "getNews: ${it.data}")
                }

                is NetworkResult.Loading -> {
                    _getNewsState.value = GetNewsState.Loading
                }

                is NetworkResult.Error -> {
                    _getNewsState.value = GetNewsState.Error(it.message!!)
                }
            }
        }
    }

    fun sortAsc() = viewModelScope.launch(Dispatchers.IO) {
        if(newsDto!=null){
            _getNewsState.value = GetNewsState.Loading
            newsDto?.articles?.sortBy {
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(it.publishedAt)?.time
            }
            _getNewsState.value = GetNewsState.Loaded(newsDto!!)
        }
    }

    fun sortDsc() = viewModelScope.launch(Dispatchers.IO) {
        if(newsDto!=null){
            _getNewsState.value = GetNewsState.Loading
            Log.d("TESTINGTAG", "sortDsc: SORTED")
            newsDto?.articles?.sortByDescending {
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(it.publishedAt)?.time
            }
            _getNewsState.value = GetNewsState.Loaded(newsDto!!)
        }
    }

    private var _savedNewsList = MutableStateFlow<List<Article>?>(null)
    val savedNewsList: StateFlow<List<Article>?>
        get() = _savedNewsList

    private fun getAllSavedNews() = viewModelScope.launch(Dispatchers.IO) {
        getAllSavedNewsUseCase().collect { list ->
            val newList = ArrayList<Article>()
            list.forEach {
                newList.add(it.toArticle())
            }
            _savedNewsList.emit(newList)
        }
    }

    fun addBookMark(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        saveNewsUseCase(article)
    }

    fun deleteBookMark(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        val item = newsDto?.articles?.find {
            it.url == article.url
        }
        item?.isBookmarked = false
        deleteNewsUseCase(article)
        newsDto?.let {
            _getNewsState.value = GetNewsState.Loaded(it)
        }
    }

}
