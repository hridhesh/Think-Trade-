package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.random.Random

data class TradingUiState(
    val points: Int = 0,
    val completedLessons: Set<String> = emptySet(),
    val completedQuizzes: Set<String> = emptySet(),
    val currentLevel: Int = 1, // 1, 2, 3
    val selectedCategory: String = "candlestick", // "candlestick" or "chart"
    
    // Quiz State
    val activeQuizQuestions: List<QuizQuestion> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedAnswerIndex: Int? = null,
    val quizSubmitted: Boolean = false,
    val quizPointsEarned: Int = 0,
    val currentQuizCompleted: Boolean = false,

    // Market Simulator Game State
    val simCandles: List<SimCandle> = emptyList(),
    val simPatternName: String = "",
    val simCorrectChoice: Int = 0, // 0: BUY/UP, 1: SELL/DOWN, 2: RANGE/SIDEWAYS
    val simResultStatus: String = "", // "idle", "correct", "wrong"
    val simExplanation: String = "",
    val isSimFinished: Boolean = false
)

data class SimCandle(
    val open: Float,
    val high: Float,
    val low: Float,
    val close: Float,
    val isGreen: Boolean
)

class TradingViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TradingRepository
    private val _uiState = MutableStateFlow(TradingUiState())
    val uiState: StateFlow<TradingUiState> = _uiState.asStateFlow()

    init {
        val database = TradingDatabase.getDatabase(application)
        repository = TradingRepository(database.userProgressDao())

        // Observe Room user state flow and map it to our UI State
        viewModelScope.launch {
            repository.progress.collect { progress ->
                _uiState.update { state ->
                    state.copy(
                        points = progress.points,
                        completedLessons = progress.completedLessons.split(",")
                            .filter { it.isNotEmpty() }.toSet(),
                        completedQuizzes = progress.completedQuizzes.split(",")
                            .filter { it.isNotEmpty() }.toSet(),
                        currentLevel = progress.activeLevel
                    )
                }
            }
        }

        // Setup the first simulator challenge
        generateNewSimulatorChallenge()
    }

    // Set Level Filter (1, 2, or 3)
    fun setLevel(level: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(currentLevel = level) }
            // Persist current looking level
            val currentProgress = repository.progress.first()
            repository.saveProgress(currentProgress.copy(activeLevel = level))
        }
    }

    // Set Category Filter
    fun setCategory(category: String) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    // Lesson Actions
    fun completeLesson(lessonId: String, reward: Int) {
        viewModelScope.launch {
            val progress = repository.progress.first()
            if (!progress.isLessonCompleted(lessonId)) {
                val updated = progress.completeLesson(lessonId, reward)
                repository.saveProgress(updated)
            }
        }
    }

    // Quiz Actions
    fun startQuizForLevel(level: Int, category: String) {
        val questions = TradingData.quizQuestions.filter { it.level == level && (category == "general" || it.category == category) }
        _uiState.update {
            it.copy(
                activeQuizQuestions = questions,
                currentQuestionIndex = 0,
                selectedAnswerIndex = null,
                quizSubmitted = false,
                quizPointsEarned = 0,
                currentQuizCompleted = false
            )
        }
    }

    fun selectQuizAnswer(index: Int) {
        if (_uiState.value.quizSubmitted) return
        _uiState.update { it.copy(selectedAnswerIndex = index) }
    }

    fun submitQuizAnswer() {
        val state = _uiState.value
        val questions = state.activeQuizQuestions
        val currentIndex = state.currentQuestionIndex
        val selectedIdx = state.selectedAnswerIndex

        if (selectedIdx == null || state.quizSubmitted) return

        val currentQuestion = questions.getOrNull(currentIndex) ?: return
        val isCorrect = selectedIdx == currentQuestion.correctAnswerIndex

        val pointReward = if (isCorrect) currentQuestion.rewardPoints else 0

        _uiState.update {
            it.copy(
                quizSubmitted = true,
                quizPointsEarned = state.quizPointsEarned + pointReward
            )
        }
    }

    fun nextQuizQuestion() {
        val state = _uiState.value
        val nextIdx = state.currentQuestionIndex + 1

        if (nextIdx >= state.activeQuizQuestions.size) {
            // End of quiz, save progress and grant rewards
            viewModelScope.launch {
                val progress = repository.progress.first()
                val quizId = "quiz_lvl${state.currentLevel}_${state.selectedCategory}"
                
                val updated = progress.completeQuiz(quizId, state.quizPointsEarned)
                repository.saveProgress(updated)
                
                _uiState.update { it.copy(currentQuizCompleted = true) }
            }
        } else {
            _uiState.update {
                it.copy(
                    currentQuestionIndex = nextIdx,
                    selectedAnswerIndex = null,
                    quizSubmitted = false
                )
            }
        }
    }

    // Market Simulator Game Actions
    fun generateNewSimulatorChallenge() {
        val random = Random(System.currentTimeMillis())
        val scenarios = listOf(
            Triple("Hammer forming at Support Floor", 0, "A beautiful Hammer is rejection testing support. This represents bullish strength, expecting a drive UP."),
            Triple("Shooting Star rejection at High ceiling", 1, "A Shooting Star rejection shows extreme exhaustion from buying. Enter shorts, expecting price to descend DOWN."),
            Triple("Ascending Triangle Consolidation", 0, "The Ascending triangle highlights rising pressure against horizontal resistance, implying a continuation UP."),
            Triple("Bearish Engulfing under Resistance", 1, "The large red body engulfs the green candle's body, confirming sellers have full authority. Price expects to head DOWN."),
            Triple("Doji indecision during clear flat Range", 2, "A Doji represents total equilibrium. Trade sideways inside a designated consolidated RANGE.")
        )

        val selectedScenario = scenarios.random()
        
        // Generate mock candlesticks leading to this event
        val count = 15
        val candles = mutableListOf<SimCandle>()
        var basePrice = 100f

        for (i in 0 until count) {
            val change = (random.nextFloat() * 8f) - 4f
            val open = basePrice
            val close = basePrice + change
            val high = maxOf(open, close) + random.nextFloat() * 3f
            val low = minOf(open, close) - random.nextFloat() * 3f
            
            candles.add(SimCandle(open, high, low, close, close >= open))
            basePrice = close
        }

        _uiState.update {
            it.copy(
                simCandles = candles,
                simPatternName = selectedScenario.first,
                simCorrectChoice = selectedScenario.second,
                simResultStatus = "idle",
                simExplanation = selectedScenario.third,
                isSimFinished = false
            )
        }
    }

    fun makeSimulatorChoice(choice: Int) {
        val state = _uiState.value
        if (state.isSimFinished) return

        val isCorrect = choice == state.simCorrectChoice
        val pointsToAward = if (isCorrect) 120 else 0

        _uiState.update {
            it.copy(
                simResultStatus = if (isCorrect) "correct" else "wrong",
                isSimFinished = true
            )
        }

        if (isCorrect) {
            viewModelScope.launch {
                val progress = repository.progress.first()
                val updated = progress.copy(points = progress.points + pointsToAward)
                repository.saveProgress(updated)
            }
        }
    }
}
