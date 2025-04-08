package com.shahbozbek.superapp.domain.usecases

import com.shahbozbek.superapp.domain.repository.Repository
import javax.inject.Inject

class GetFavouriteNewsUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke() = repository.getFavouriteArticles()
}