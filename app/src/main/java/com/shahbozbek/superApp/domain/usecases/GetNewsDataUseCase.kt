package com.shahbozbek.superApp.domain.usecases

import com.shahbozbek.superApp.domain.model.newsData.Article
import com.shahbozbek.superApp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsDataUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(category: String = ""): Flow<List<Article>> {
        return repository.getNewsData(category = category)
    }
}