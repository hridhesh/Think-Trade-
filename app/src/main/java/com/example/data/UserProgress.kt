package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "user_progress")
data class UserProgress(
    @PrimaryKey val id: Int = 1,
    val points: Int = 0,
    val completedLessons: String = "", // comma-separated lesson IDs
    val completedQuizzes: String = "", // comma-separated quiz IDs
    val activeLevel: Int = 1
) {
    fun isLessonCompleted(lessonId: String): Boolean {
        return completedLessons.split(",").contains(lessonId)
    }

    fun isQuizCompleted(quizId: String): Boolean {
        return completedQuizzes.split(",").contains(quizId)
    }

    fun completeLesson(lessonId: String, reward: Int): UserProgress {
        if (isLessonCompleted(lessonId)) return this
        val newLessons = if (completedLessons.isEmpty()) lessonId else "$completedLessons,$lessonId"
        return this.copy(
            points = this.points + reward,
            completedLessons = newLessons
        )
    }

    fun completeQuiz(quizId: String, reward: Int): UserProgress {
        if (isQuizCompleted(quizId)) return this
        val newQuizzes = if (completedQuizzes.isEmpty()) quizId else "$completedQuizzes,$quizId"
        return this.copy(
            points = this.points + reward,
            completedQuizzes = newQuizzes
        )
    }
}

@Dao
interface UserProgressDao {
    @Query("SELECT * FROM user_progress WHERE id = 1 LIMIT 1")
    fun getProgress(): Flow<UserProgress?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProgress(progress: UserProgress)

    @Transaction
    suspend fun addPoints(pointsToAdd: Int) {
        // Direct suspension operations
    }
}
