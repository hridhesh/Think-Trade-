package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TradingData
import com.example.ui.TradingUiState
import com.example.ui.components.GreenCandle

@Composable
fun ProfileScreen(
    state: TradingUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC)) // Clear Slate 50 background
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Upper stats banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF4F46E5)), // Indigo 600 solid background
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar circle
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.AccountBox,
                        contentDescription = "Avatar",
                        tint = Color(0xFF4F46E5), // Indigo icon tint
                        modifier = Modifier.size(36.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Elite Pro Trader",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Consolidated Status: LEVEL ${state.currentLevel}",
                        fontSize = 12.sp,
                        color = Color(0xFFFBBF24), // Gold Amber text contrast
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }

        // Stats boxes grid
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0)) // Clean card border
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("REWARD POINTS", fontSize = 11.sp, color = Color(0xFF64748B)) // Slate 500
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${state.points} XP",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4F46E5), // Indigo Accent
                        modifier = Modifier.testTag("user_total_points")
                    )
                }
            }

            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0)) // Clean card border
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("COMPLETED WORK", fontSize = 11.sp, color = Color(0xFF64748B)) // Slate 500
                    Spacer(modifier = Modifier.height(4.dp))
                    val completeTotal = state.completedLessons.size + state.completedQuizzes.size
                    Text(
                        text = "$completeTotal tasks",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF10B981) // Emerald Green
                    )
                }
            }
        }

        // Achievements Section Title
        Text(
            text = "Trading Milestones",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A), // Slate 900
            modifier = Modifier.padding(top = 8.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(TradingData.achievements) { achievement ->
                val isUnlocked = state.points >= achievement.pointsRequired
                
                Card(
                     modifier = Modifier
                        .fillMaxWidth()
                        .testTag("achievement_${achievement.id}"),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isUnlocked) Color.White else Color(0xFFF1F5F9) // White if active, slate 100 if locked
                    ),
                    border = if (isUnlocked) {
                        BorderStroke(1.dp, Color(0xFF10B981).copy(alpha = 0.5f))
                    } else {
                        BorderStroke(1.dp, Color(0xFFE2E8F0))
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Badge Status Graphics
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(if (isUnlocked) Color(0xFFD1FAE5) else Color(0xFFE2E8F0)), // Light Emerald / slate 200 grid
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (achievement.iconName == "school") Icons.Default.Star 
                                             else if (achievement.iconName == "trending_up") Icons.Default.KeyboardArrowUp 
                                             else Icons.Default.ThumbUp,
                                contentDescription = achievement.title,
                                tint = if (isUnlocked) Color(0xFF10B981) else Color(0xFF94A3B8),
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = achievement.title,
                                color = if (isUnlocked) Color(0xFF0F172A) else Color(0xFF94A3B8),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = achievement.description,
                                color = if (isUnlocked) Color(0xFF475569) else Color(0xFF94A3B8),
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            if (!isUnlocked) {
                                LinearProgressIndicator(
                                    progress = { state.points.toFloat() / achievement.pointsRequired.toFloat() },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(6.dp)
                                        .clip(RoundedCornerShape(3.dp)),
                                    color = Color(0xFF4F46E5),
                                    trackColor = Color(0xFFE2E8F0)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Requires ${achievement.pointsRequired} XP (Current: ${state.points})",
                                    fontSize = 10.sp,
                                    color = Color(0xFF64748B)
                                )
                            } else {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Check, contentDescription = "Unlocked", tint = Color(0xFF10B981), modifier = Modifier.size(12.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Claimed & Unlocked", color = Color(0xFF10B981), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
