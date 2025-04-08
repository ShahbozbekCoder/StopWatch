package com.shahbozbek.superapp.domain.usecases

import com.shahbozbek.superapp.domain.model.newsData.Article
import com.shahbozbek.superapp.domain.repository.Repository
import javax.inject.Inject

class InsertFavouriteArticleUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(article: Article) = repository.insertFavouriteArticle(article)
}