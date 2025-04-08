package com.shahbozbek.superapp.domain.usecases

import com.shahbozbek.superapp.domain.repository.Repository
import javax.inject.Inject

class SaveTimeUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(time: Long) = repository.saveTime(time = time)
}