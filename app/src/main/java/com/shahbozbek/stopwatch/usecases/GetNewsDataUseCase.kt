package com.shahbozbek.stopwatch.usecases

import com.shahbozbek.stopwatch.data.models.newsdata.Article
import com.shahbozbek.stopwatch.data.models.newsdata.NewsData
import com.shahbozbek.stopwatch.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsDataUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(category: String = ""): Flow<List<Article>> {
        return repository.getNewsData(category = category)
    }
}