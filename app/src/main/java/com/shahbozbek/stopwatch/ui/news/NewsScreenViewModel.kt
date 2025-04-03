package com.shahbozbek.stopwatch.ui.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahbozbek.stopwatch.data.models.newsdata.Article
import com.shahbozbek.stopwatch.data.models.newsdata.FavouriteArticle
import com.shahbozbek.stopwatch.data.models.newsdata.NewsData
import com.shahbozbek.stopwatch.ui.theme.ThemePreferences
import com.shahbozbek.stopwatch.usecases.DeleteFavouriteArticleUseCase
import com.shahbozbek.stopwatch.usecases.GetFavouriteNewsUseCase
import com.shahbozbek.stopwatch.usecases.GetNewsDataUseCase
import com.shahbozbek.stopwatch.usecases.InsertFavouriteArticleUseCase
import com.shahbozbek.stopwatch.utils.Result
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
    private val themePreferences: ThemePreferences
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
        Log.d("VVVVV", "getNewsData: error")
        _newsData.value = Result.Error(throwable.message ?: "Unknown error")
    }

    private val _isDarkThemeEnabled = MutableStateFlow(themePreferences.isDarkThemeEnabled())
    val isDarkThemeEnabled: StateFlow<Boolean> get() = _isDarkThemeEnabled.asStateFlow()

//    fun getNews(category: String = "") {
//        viewModelScope.launch(coroutineExceptionHandler) {
//
//            _newsData.value = Result.Loading
//
//            getNewsDataUseCase.invoke(category).collect { newsData ->
//                _newsData.value = Result.Success(newsData)
//            }
//        }
//    }

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

    fun getFavouriteNews() = getFavouriteNewsUseCase.invoke()

    fun getNewsData(category: String = "General") {

        viewModelScope.launch(coroutineExceptionHandler) {
            selectedCategory.value = category

            getNewsDataUseCase.invoke(category).collectLatest { news ->
                Log.d("VVVVV", "getNewsData: sucess")
                _newsData.value = Result.Success(NewsData(news))
            }
        }
    }

    private var myFavourite: Article? = null

    fun setFavourite(article: Article) {
        myFavourite = article
    }

    fun getFavourite(): Article? {
        return myFavourite
    }


}