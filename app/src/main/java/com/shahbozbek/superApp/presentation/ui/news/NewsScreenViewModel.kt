package com.shahbozbek.superApp.presentation.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahbozbek.superApp.data.remote.NetworkUtils
import com.shahbozbek.superApp.domain.model.newsData.Article
import com.shahbozbek.superApp.domain.model.newsData.FavouriteArticle
import com.shahbozbek.superApp.domain.model.newsData.NewsData
import com.shahbozbek.superApp.domain.usecases.DeleteFavouriteArticleUseCase
import com.shahbozbek.superApp.domain.usecases.GetFavouriteNewsUseCase
import com.shahbozbek.superApp.domain.usecases.GetNewsDataUseCase
import com.shahbozbek.superApp.domain.usecases.InsertFavouriteArticleUseCase
import com.shahbozbek.superApp.presentation.ui.theme.ThemePreferences
import com.shahbozbek.superApp.presentation.utils.Result
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
    private val deleteFavouriteArticleUseCase: DeleteFavouriteArticleUseCase,
    private val themePreferences: ThemePreferences,
    private val networkUtils: NetworkUtils
) : ViewModel() {

    val listOfCategory: StateFlow<List<String>> = MutableStateFlow(
        listOf(
            "General",
            "Business",
            "Entertainment",
            "Health",
            "Science",
            "Sports",
            "Technology"
        )
    )

    val selectedCategory: MutableStateFlow<String> = MutableStateFlow("General")

    private val _newsData = MutableStateFlow<Result>(Result.Loading)
    val newsData: StateFlow<Result> get() = _newsData.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _newsData.value = Result.Error(throwable.message ?: "Unknown error")
    }

    private val _isDarkThemeEnabled = MutableStateFlow(themePreferences.isDarkThemeEnabled())
    val isDarkThemeEnabled: StateFlow<Boolean> get() = _isDarkThemeEnabled.asStateFlow()

    init {
        getNewsData()
    }

    fun insertFavouriteArticle(articleDto: Article) {
        viewModelScope.launch {
            insertFavouriteArticleUseCase(articleDto)
        }
    }

    fun deleteFavouriteArticle(article: FavouriteArticle) {
        viewModelScope.launch {
            deleteFavouriteArticleUseCase(article)
        }
    }

    fun getFavouriteNews() = getFavouriteNewsUseCase.invoke()

    fun getNewsData(category: String = "General") {

        viewModelScope.launch(coroutineExceptionHandler) {

            _newsData.value = Result.Loading

            selectedCategory.value = category

            getNewsDataUseCase.invoke(category).collectLatest { news ->
                _newsData.value = Result.Success(NewsData(news))
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