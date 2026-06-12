package com.example.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TradingRepository(private val dao: UserProgressDao) {
    val progress: Flow<UserProgress> = dao.getProgress().map { it ?: UserProgress() }

    suspend fun saveProgress(userProgress: UserProgress) {
        dao.saveProgress(userProgress)
    }
}
