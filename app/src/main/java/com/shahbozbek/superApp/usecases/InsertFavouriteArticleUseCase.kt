package com.shahbozbek.superApp.usecases

import com.shahbozbek.superApp.data.models.newsdata.Article
import com.shahbozbek.superApp.repository.Repository
import javax.inject.Inject

class InsertFavouriteArticleUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(article: Article) = repository.insertFavouriteArticle(article)
}