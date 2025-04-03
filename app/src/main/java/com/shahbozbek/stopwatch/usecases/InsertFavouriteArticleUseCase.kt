package com.shahbozbek.stopwatch.usecases

import com.shahbozbek.stopwatch.data.models.newsdata.Article
import com.shahbozbek.stopwatch.repository.Repository
import javax.inject.Inject

class InsertFavouriteArticleUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(article: Article) = repository.insertFavouriteArticle(article)
}