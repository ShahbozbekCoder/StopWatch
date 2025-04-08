package com.shahbozbek.superapp.presentation.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahbozbek.superapp.domain.model.newsData.Article
import com.shahbozbek.superapp.domain.model.newsData.FavouriteArticle
import com.shahbozbek.superapp.domain.model.newsData.NewsData
import com.shahbozbek.superapp.domain.usecases.DeleteFavouriteArticleUseCase
import com.shahbozbek.superapp.domain.usecases.GetFavouriteNewsUseCase
import com.shahbozbek.superapp.domain.usecases.GetNewsDataUseCase
import com.shahbozbek.superapp.domain.usecases.InsertFavouriteArticleUseCase
import com.shahbozbek.superapp.presentation.utils.Constants.categories
import com.shahbozbek.superapp.presentation.utils.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsScreenViewModel @Inject constructor(
    private val getNewsDataUseCase: GetNewsDataUseCase,
    private val getFavouriteNewsUseCase: GetFavouriteNewsUseCase,
    private val insertFavouriteArticleUseCase: InsertFavouriteArticleUseCase,
    private val deleteFavouriteArticleUseCase: DeleteFavouriteArticleUseCase
) : ViewModel() {

    val listOfCategory: StateFlow<List<String>> = MutableStateFlow(categories)

    val selectedCategory: MutableStateFlow<String> = MutableStateFlow("General")

    private val _newsData = MutableStateFlow<Results>(Results.Loading)
    val newsData: StateFlow<Results> get() = _newsData.asStateFlow()

    private val _favoriteData = MutableStateFlow<List<FavouriteArticle>>(listOf())
    val favouritesData: StateFlow<List<FavouriteArticle>> get() = _favoriteData.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _newsData.value = Results.Error(throwable.message ?: "Unknown error")
    }

    init {
        getNewsData()
    }

    fun insertFavouriteArticle(article: Article) {
        viewModelScope.launch {
            insertFavouriteArticleUseCase(article)
        }
    }

    fun deleteFavouriteArticle(article: FavouriteArticle) {
        viewModelScope.launch {
            deleteFavouriteArticleUseCase(article)
        }
    }

    fun getFavouriteNews() {
        viewModelScope.launch {
            getFavouriteNewsUseCase().collectLatest {
                _favoriteData.value = it
            }
        }
    }

    fun getNewsData(category: String = "General", isOnline: Boolean = true) {

        viewModelScope.launch(coroutineExceptionHandler) {

            _newsData.value = Results.Loading

            selectedCategory.value = category

            getNewsDataUseCase.invoke(category = category, isOnline = isOnline)
                .collectLatest { news ->
                    _newsData.value = Results.Success(NewsData(news))
                }
        }
    }

    private var myFavourite: Article? = null

    fun setArticle(articleDto: Article) {
        myFavourite = articleDto
    }

    fun getArticle(): Article? {
        return myFavourite
    }

}