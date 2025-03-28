package com.shahbozbek.stopwatch.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahbozbek.stopwatch.data.models.newsdata.Article
import com.shahbozbek.stopwatch.repository.RepositoryImpl
import com.shahbozbek.stopwatch.usecases.GetFavouriteNewsUseCase
import com.shahbozbek.stopwatch.usecases.GetNewsDataUseCase
import com.shahbozbek.stopwatch.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsScreenViewModel @Inject constructor(
    private val getNewsDataUseCase: GetNewsDataUseCase,
    private val repositoryImpl: RepositoryImpl,
    private val getFavouriteNewsUseCase: GetFavouriteNewsUseCase
) : ViewModel() {

    private val _newsData = MutableStateFlow<Result>(Result.Loading)
    val newsData: StateFlow<Result> get() = _newsData

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _newsData.value = Result.Error(throwable.message ?: "Unknown error")
    }

    fun getNews(category: String = "") {
        viewModelScope.launch(coroutineExceptionHandler) {

            _newsData.value = Result.Loading

            getNewsDataUseCase.invoke(category).collect { newsData ->
                _newsData.value = Result.Success(newsData)
            }
        }
    }

    fun insertFavouriteArticle(article: Article) {
        viewModelScope.launch {
            repositoryImpl.insertFavouriteArticle(article)
        }
    }

    fun deleteFavouriteArticle(article: Article) {
        viewModelScope.launch {
            repositoryImpl.deleteFavouriteArticles(article)
        }
    }

    fun getFavouriteNews() = getFavouriteNewsUseCase.invoke()

    private var myFavourite: Article? = null

    fun setFavourite(article: Article) {
        myFavourite = article
    }

    fun getFavourite(): Article? {
        return myFavourite
    }


}