package com.shahbozbek.superApp.domain.usecases

import com.shahbozbek.superApp.domain.model.newsData.FavouriteArticle
import com.shahbozbek.superApp.domain.repository.Repository
import javax.inject.Inject

class DeleteFavouriteArticleUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(article: FavouriteArticle) =
        repository.deleteFavouriteArticles(article)
}