package com.shahbozbek.superApp.domain.usecases

import com.shahbozbek.superApp.domain.repository.Repository
import javax.inject.Inject

class GetFavouriteNewsUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke() = repository.getFavouriteArticles()
}