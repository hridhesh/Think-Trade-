package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TradingData
import com.example.ui.TradingUiState
import com.example.ui.components.GreenCandle
import com.example.ui.components.RedCandle

@Composable
fun QuizScreen(
    state: TradingUiState,
    onStartQuiz: (Int, String) -> Unit,
    onSelectAnswer: (Int) -> Unit,
    onSubmitAnswer: () -> Unit,
    onNextQuestion: () -> Unit,
    modifier: Modifier = Modifier
) {
    val quizId = "quiz_lvl${state.currentLevel}_${state.selectedCategory}"
    val isQuizCompleted = state.completedQuizzes.contains(quizId)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC)) // Clear Slate 50 background
            .padding(16.dp)
    ) {
        if (state.activeQuizQuestions.isEmpty() || state.currentQuizCompleted) {
            // QUIZ DASHBOARD
            Text(
                text = "Quiz Practice Arena",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A), // Slate 900
                modifier = Modifier.padding(bottom = 6.dp)
            )
            Text(
                text = "Test your skills for level ${state.currentLevel} and gain massive reward points!",
                fontSize = 13.sp,
                color = Color(0xFF64748B), // Slate 500
                modifier = Modifier.padding(bottom = 20.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0)), // Delicate Border
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Level ${state.currentLevel} Exam",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A)
                        )
                        if (isQuizCompleted) {
                            Badge(containerColor = GreenCandle, contentColor = Color.White) {
                                Text("COMPLETED", fontWeight = FontWeight.Bold, modifier = Modifier.padding(4.dp))
                            }
                        } else {
                            Badge(containerColor = Color(0xFF4F46E5), contentColor = Color.White) {
                                Text("READY", fontWeight = FontWeight.Bold, modifier = Modifier.padding(4.dp))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "This exam certifies your comprehension of ${
                            if (state.selectedCategory == "candlestick") "Candlestick formations (Hammers, Dojis, stars)" 
                            else "Chart setups (Tops, bottoms, flags)"
                        } at level ${state.currentLevel}.",
                        fontSize = 13.sp,
                        color = Color(0xFF64748B) // Slate 500
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { onStartQuiz(state.currentLevel, state.selectedCategory) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("start_quiz_btn"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isQuizCompleted) Color(0xFF94A3B8) else Color(0xFF4F46E5),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = "Run", modifier = Modifier.size(18.dp), tint = Color.White)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isQuizCompleted) "Retake Quiz" else "Start Examination",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Quick Tips Card (styled in Amber 50 to match design specifications)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)), // Amber 50
                border = BorderStroke(1.dp, Color(0xFFFDE68A)), // Amber 200
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Info, contentDescription = "Tip", tint = Color(0xFFD97706), modifier = Modifier.size(28.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Traders' Insight:", fontWeight = FontWeight.Bold, color = Color(0xFFB45309), fontSize = 14.sp)
                        Text("Accurate answers earn up to 100 points per question. Keep repeating quizzes to commit these pattern structures to visual reflex!", color = Color(0xFF92400E), fontSize = 12.sp)
                    }
                }
            }
        } else {
            // LIVE QUIZ EXECUTIVE
            val question = state.activeQuizQuestions.getOrNull(state.currentQuestionIndex)
            if (question != null) {
                // Progress tracker bar
                val progress = (state.currentQuestionIndex + 1).toFloat() / state.activeQuizQuestions.size.toFloat()
                
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Question ${state.currentQuestionIndex + 1} of ${state.activeQuizQuestions.size}",
                        color = Color(0xFF0F172A),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "XP Earned: +${state.quizPointsEarned} XP",
                        color = Color(0xFFD97706),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .padding(bottom = 20.dp),
                    color = Color(0xFF4F46E5),
                    trackColor = Color(0xFFE2E8F0)
                )

                Text(
                    text = question.questionText,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A),
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                // Render matching multiple choice elements
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(question.options.size) { idx ->
                        val optionText = question.options[idx]
                        val isSelected = state.selectedAnswerIndex == idx
                        val isSubmitted = state.quizSubmitted
                        val isCorrectAns = idx == question.correctAnswerIndex

                        val background = when {
                            isSubmitted && isCorrectAns -> GreenCandle.copy(alpha = 0.15f)
                            isSubmitted && isSelected && !isCorrectAns -> RedCandle.copy(alpha = 0.12f)
                            isSelected -> Color(0xFFEEF2FF) // Indigo 50 background for active choice list items
                            else -> Color.White
                        }

                        val borderColor = when {
                            isSubmitted && isCorrectAns -> GreenCandle
                            isSubmitted && isSelected && !isCorrectAns -> RedCandle
                            isSelected -> Color(0xFF4F46E5)
                            else -> Color(0xFFE2E8F0)
                        }

                        val contentColor = when {
                            isSubmitted && isCorrectAns -> GreenCandle
                            isSubmitted && isSelected && !isCorrectAns -> RedCandle
                            isSelected -> Color(0xFF4F46E5)
                            else -> Color(0xFF0F172A)
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(background, shape = RoundedCornerShape(12.dp))
                                .border(1.dp, borderColor, shape = RoundedCornerShape(12.dp))
                                .clickable(enabled = !isSubmitted) { onSelectAnswer(idx) }
                                .padding(16.dp)
                                .testTag("quiz_option_$idx"),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${('A' + idx)}.  $optionText",
                                color = contentColor,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.weight(1f)
                            )
                            if (isSubmitted) {
                                if (isCorrectAns) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = "Correct", tint = GreenCandle, modifier = Modifier.size(20.dp))
                                } else if (isSelected) {
                                    Icon(Icons.Default.Close, contentDescription = "Incorrect", tint = RedCandle, modifier = Modifier.size(20.dp))
                                }
                            }
                        }
                    }

                    // Educational Explanation
                    if (state.quizSubmitted) {
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFEEF2FF)), // Light Indigo 50 text alert field back
                                border = BorderStroke(1.dp, Color(0xFFC7D2FE))
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text(
                                        text = "Trading Analysis Explanation:",
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF4F46E5),
                                        fontSize = 13.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = question.explanation,
                                        color = Color(0xFF334155),
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        }
                    }
                }

                // Call to actions
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (!state.quizSubmitted) {
                        Button(
                            onClick = onSubmitAnswer,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("submit_quiz_answer"),
                            enabled = state.selectedAnswerIndex != null,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4F46E5),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Submit Selection", fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Button(
                            onClick = onNextQuestion,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("next_quiz_question"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4F46E5),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = if (state.currentQuestionIndex == state.activeQuizQuestions.size - 1) "Complete Quiz" else "Continue Exam",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
