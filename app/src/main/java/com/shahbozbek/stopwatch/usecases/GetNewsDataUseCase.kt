package com.shahbozbek.stopwatch.usecases

import com.shahbozbek.stopwatch.data.models.newsdata.NewsData
import com.shahbozbek.stopwatch.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsDataUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(category: String = ""): Flow<NewsData?> {
        return repository.getNews(category = category)
    }
}