package com.shahbozbek.superApp.usecases

import com.shahbozbek.superApp.data.models.newsdata.Article
import com.shahbozbek.superApp.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsDataUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(category: String = ""): Flow<List<Article>> {
        return repository.getNewsData(category = category)
    }
}