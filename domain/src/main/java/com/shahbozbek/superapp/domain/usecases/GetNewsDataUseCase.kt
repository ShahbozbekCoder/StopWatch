package com.shahbozbek.superapp.domain.usecases

import com.shahbozbek.superapp.domain.model.newsData.Article
import com.shahbozbek.superapp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsDataUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(
        category: String = "",
        isOnline: Boolean = true
    ): Flow<List<Article>> {
        return repository.getNewsData(category = category, isOnline = isOnline)
    }
}