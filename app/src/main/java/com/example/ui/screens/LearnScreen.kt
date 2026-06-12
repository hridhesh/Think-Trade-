package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PatternLesson
import com.example.data.TradingData
import com.example.ui.TradingUiState
import com.example.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnScreen(
    state: TradingUiState,
    onCategorySelected: (String) -> Unit,
    onLevelSelected: (Int) -> Unit,
    onLessonCompleted: (String, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedLesson by remember { mutableStateOf<PatternLesson?>(null) }

    val lessons = remember(state.selectedCategory, state.currentLevel) {
        if (state.selectedCategory == "candlestick") {
            TradingData.candlestickLessons.filter { it.level == state.currentLevel }
        } else {
            TradingData.chartLessons.filter { it.level == state.currentLevel }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC)) // Clean Slate 50 background
            .padding(16.dp)
    ) {
        // Mode Header Cards (Level & Category select)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Category Buttons (Polished border styles)
            Button(
                onClick = { onCategorySelected("candlestick") },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("candlestick_category_btn"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (state.selectedCategory == "candlestick") Color(0xFF4F46E5) else Color.White,
                    contentColor = if (state.selectedCategory == "candlestick") Color.White else Color(0xFF0F172A)
                ),
                border = if (state.selectedCategory == "candlestick") null else BorderStroke(1.dp, Color(0xFFE2E8F0)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("🕯️", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Candlestick", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }

            Button(
                onClick = { onCategorySelected("chart") },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("chart_category_btn"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (state.selectedCategory == "chart") Color(0xFF4F46E5) else Color.White,
                    contentColor = if (state.selectedCategory == "chart") Color.White else Color(0xFF0F172A)
                ),
                border = if (state.selectedCategory == "chart") null else BorderStroke(1.dp, Color(0xFFE2E8F0)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("📊", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Chart Patterns", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }

        // 3-Level Segmented Control
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFEEF2FF)), // Light Indigo 50 base background
            shape = RoundedCornerShape(14.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                listOf(
                    1 to "Level 1: Beginner",
                    2 to "Level 2: Intermediate",
                    3 to "Level 3: Advanced"
                ).forEach { (level, label) ->
                    val isSelected = state.currentLevel == level
                    Box(
                        modifier = Modifier
                          .weight(1f)
                          .height(36.dp)
                          .background(
                              color = if (isSelected) Color(0xFF4F46E5) else Color.Transparent,
                              shape = RoundedCornerShape(10.dp)
                          )
                          .clickable { onLevelSelected(level) }
                          .wrapContentHeight(Alignment.CenterVertically)
                          .testTag("level_btn_$level"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label.replace("Level ", "L"),
                            color = if (isSelected) Color.White else Color(0xFF4F46E5),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        // List View of Current course
        if (lessons.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text("No patterns available for this tier yet.", color = Color(0xFF64748B))
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(lessons) { lesson ->
                    val isCompleted = state.completedLessons.contains(lesson.id)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedLesson = lesson }
                            .testTag("lesson_card_${lesson.id}"),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(16.dp),
                        border = if (isCompleted) {
                            BorderStroke(2.dp, GreenCandle)
                        } else {
                            BorderStroke(1.dp, Color(0xFFE2E8F0))
                        }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = lesson.title,
                                        color = Color(0xFF0F172A), // Slate 900
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    if (isCompleted) {
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Icon(
                                            Icons.Default.CheckCircle,
                                            contentDescription = "Completed",
                                            tint = GreenCandle,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = lesson.shortDesc,
                                    color = Color(0xFF64748B), // Slate 500
                                    fontSize = 13.sp,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    SuggestionChip(
                                        onClick = {},
                                        label = { Text(lesson.marketSignal, fontSize = 11.sp) },
                                        colors = SuggestionChipDefaults.suggestionChipColors(
                                            labelColor = if (lesson.marketSignal.contains("Bullish")) GreenCandle else RedCandle
                                        )
                                    )
                                    Text(
                                        text = "+${lesson.rewardPoints} XP",
                                        color = Color(0xFFD97706), // Amber-600 gold reward representation
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Icon(
                                Icons.Default.PlayArrow,
                                contentDescription = "View details",
                                tint = Color(0xFF4F46E5), // Indigo indicator arrow
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }

        // Pattern Detail / Learning Bottom Draw Sheet
        selectedLesson?.let { lesson ->
            AlertDialog(
                onDismissRequest = { selectedLesson = null },
                confirmButton = {
                    val isCompleted = state.completedLessons.contains(lesson.id)
                    Button(
                        onClick = {
                            if (!isCompleted) {
                                onLessonCompleted(lesson.id, lesson.rewardPoints)
                            }
                            selectedLesson = null
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isCompleted) Color(0xFF94A3B8) else Color(0xFF10B981),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.testTag("complete_lesson_action")
                    ) {
                        Text(if (isCompleted) "Done" else "Understand & Claim +${lesson.rewardPoints} XP", fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { selectedLesson = null }) {
                        Text("Close", color = Color(0xFF4F46E5), fontWeight = FontWeight.Bold)
                    }
                },
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = lesson.title, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF0F172A))
                        SuggestionChip(
                            onClick = {},
                            label = { Text(lesson.marketSignal) },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                labelColor = if (lesson.marketSignal.contains("Bullish")) GreenCandle else RedCandle
                            )
                        )
                    }
                },
                text = {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Text(
                                text = "Visual Pattern Structure:",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A),
                                fontSize = 14.sp
                            )
                        }
                        item {
                            // Render the dedicated programmatic Canvas pattern
                            if (lesson.category == "candlestick") {
                                CandlestickChart(patternType = lesson.patternType)
                            } else {
                                PatternChart(patternType = lesson.patternType)
                            }
                        }
                        item {
                            Text(
                                text = "How it works:",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A),
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = lesson.description, color = Color(0xFF334155), fontSize = 13.sp)
                        }
                        item {
                            Text(
                                text = "How to execution trading setups:",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A),
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = lesson.howToTrade, color = Color(0xFF334155), fontSize = 13.sp)
                        }
                    }
                },
                containerColor = Color.White, // Premium White popup dialog as per the Light Theme specification
                shape = RoundedCornerShape(24.dp)
            )
        }
    }
}
