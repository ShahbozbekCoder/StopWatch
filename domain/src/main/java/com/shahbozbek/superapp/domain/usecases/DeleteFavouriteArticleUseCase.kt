package com.shahbozbek.superapp.domain.usecases

import com.shahbozbek.superapp.domain.repository.Repository
import javax.inject.Inject

class DeleteFavouriteArticleUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(article: com.shahbozbek.superapp.domain.model.newsData.FavouriteArticle) =
        repository.deleteFavouriteArticles(article)
}