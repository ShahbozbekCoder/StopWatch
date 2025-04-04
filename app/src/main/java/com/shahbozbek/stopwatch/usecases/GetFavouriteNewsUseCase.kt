package com.shahbozbek.stopwatch.usecases

import com.shahbozbek.stopwatch.repository.Repository
import javax.inject.Inject

class GetFavouriteNewsUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke() = repository.getFavouriteArticles()
}