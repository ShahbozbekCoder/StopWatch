package com.shahbozbek.superApp.usecases

import com.shahbozbek.superApp.data.models.newsdata.FavouriteArticle
import com.shahbozbek.superApp.repository.Repository
import javax.inject.Inject

class DeleteFavouriteArticleUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(article: FavouriteArticle) =
        repository.deleteFavouriteArticles(article)
}