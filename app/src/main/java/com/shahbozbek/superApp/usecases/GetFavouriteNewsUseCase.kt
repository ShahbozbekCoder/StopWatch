package com.shahbozbek.superApp.usecases

import com.shahbozbek.superApp.repository.Repository
import javax.inject.Inject

class GetFavouriteNewsUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke() = repository.getFavouriteArticles()
}