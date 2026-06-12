package com.example.data

import androidx.compose.ui.graphics.Color

data class PatternLesson(
    val id: String,
    val title: String,
    val category: String, // "chart" or "candlestick"
    val level: Int, // 1 (Beginner), 2 (Intermediate), 3 (Advanced)
    val shortDesc: String,
    val description: String,
    val patternType: String, // Identifies how the Canvas component should draw it
    val marketSignal: String, // e.g. "Bullish Reversal", "Bearish Continuation"
    val howToTrade: String,
    val rewardPoints: Int = 50
)

data class QuizQuestion(
    val id: String,
    val level: Int,
    val category: String, // "chart", "candlestick", or "general"
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String,
    val rewardPoints: Int = 100
)

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val pointsRequired: Int,
    val iconName: String
)
