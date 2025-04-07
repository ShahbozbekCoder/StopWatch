package com.shahbozbek.superApp.domain.usecases

import com.shahbozbek.superApp.domain.model.newsData.Article
import com.shahbozbek.superApp.domain.repository.Repository
import javax.inject.Inject

class InsertFavouriteArticleUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(articleDto: Article) = repository.insertFavouriteArticle(articleDto)
}