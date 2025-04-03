package com.shahbozbek.stopwatch.usecases

import com.shahbozbek.stopwatch.data.models.newsdata.FavouriteArticle
import com.shahbozbek.stopwatch.repository.Repository
import javax.inject.Inject

class DeleteFavouriteArticleUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(article: FavouriteArticle) =
        repository.deleteFavouriteArticles(article)
}