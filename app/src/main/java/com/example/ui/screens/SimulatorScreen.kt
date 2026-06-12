package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.TradingUiState
import com.example.ui.components.DarkBackground
import com.example.ui.components.GreenCandle
import com.example.ui.components.RedCandle
import com.example.ui.components.MutedText

@Composable
fun SimulatorScreen(
    state: TradingUiState,
    onMakeChoice: (Int) -> Unit,
    onNextChallenge: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC)) // Clear Slate 50 background
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Active simulator title
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Live Market Simulator",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A) // Slate 900
                )
                Text(
                    text = "Analyze live structures & make execution choices",
                    fontSize = 12.sp,
                    color = Color(0xFF64748B) // Slate 500
                )
            }
            // Reward badges
            Badge(containerColor = Color(0xFFFEF3C7)) { // Amber 100
                Text("PRO SETUP +120 XP", fontWeight = FontWeight.Bold, color = Color(0xFFB45309), modifier = Modifier.padding(6.dp))
            }
        }

        // Programmatic simulated active candlestick chart
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .border(1.dp, Color(0xFFE2E8F0), shape = RoundedCornerShape(16.dp))
                .padding(12.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height
                val candles = state.simCandles

                // Draw technical grid lines (Slate 100/200 style)
                for (i in 1..4) {
                    val y = height * (i / 5f)
                    drawLine(
                        color = Color(0xFFE2E8F0),
                        start = Offset(0f, y),
                        end = Offset(width, y),
                        strokeWidth = 1f
                    )
                }

                if (candles.isNotEmpty()) {
                    val spacing = width / candles.size
                    val candleWidth = spacing * 0.6f

                    candles.forEachIndexed { index, candle ->
                        val x = (index * spacing) + (spacing / 2f)
                        
                        // Map 90-110 price range to screen coordinate space
                        val mapY = { price: Float ->
                            val normalized = (price - 90f) / 20f
                            height - (normalized * height)
                        }

                        val topY = mapY(maxOf(candle.open, candle.close))
                        val bottomY = mapY(minOf(candle.open, candle.close))
                        val highY = mapY(candle.high)
                        val lowY = mapY(candle.low)
                        
                        val candleColor = if (candle.isGreen) GreenCandle else RedCandle

                        // Draw Wick
                        drawLine(
                            color = candleColor,
                            start = Offset(x, highY),
                            end = Offset(x, lowY),
                            strokeWidth = 2.dp.toPx()
                        )

                        // Draw Candle body
                        drawRect(
                            color = candleColor,
                            topLeft = Offset(x - (candleWidth / 2f), topY),
                            size = Size(candleWidth, maxOf(bottomY - topY, 2.dp.toPx()))
                        )
                    }
                }
            }

            // Radar scan graphic overlay (Clean Slate style badge)
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .background(Color(0xFFEEF2FF), shape = RoundedCornerShape(8.dp)) // Indigo 50 background
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = "Live",
                        tint = Color(0xFF10B981), // Green bullet
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "INSTRUMENT: SPY/BTC INDEX",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4F46E5), // Indigo text
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }

        // Live Analyzer Readout Console
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Info, contentDescription = "Console", tint = Color(0xFF4F46E5), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "PATTERN RECON UNLOCKED:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4F46E5),
                        fontFamily = FontFamily.Monospace
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = state.simPatternName,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "A structure has completed forming. Decide your strategic trade execution using the terminal triggers below.",
                    fontSize = 12.sp,
                    color = Color(0xFF64748B)
                )
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            if (state.isSimFinished) {
                // Choice evaluate review terminal
                val wasCorrect = state.simResultStatus == "correct"
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = if (wasCorrect) Color(0xFFD1FAE5) else Color(0xFFFFE4E6), // Light Green / Light Red
                            shape = RoundedCornerShape(16.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = if (wasCorrect) Color(0xFF10B981) else Color(0xFFF43F5E),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (wasCorrect) Icons.Default.CheckCircle else Icons.Default.Close,
                            contentDescription = "Status",
                            tint = if (wasCorrect) Color(0xFF047857) else Color(0xFFBE123C),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (wasCorrect) "SUCCESSFUL EXECUTION (+120 XP Claimed)" else "EXECUTION REJECTED (Stop-Loss Triggered)",
                            fontWeight = FontWeight.Bold,
                            color = if (wasCorrect) Color(0xFF047857) else Color(0xFFBE123C),
                            fontSize = 13.sp
                        )
                    }

                    Text(
                        text = state.simExplanation,
                        color = Color(0xFF334155), // Slate 700 text color
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Button(
                        onClick = onNextChallenge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("next_challenge_action"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (wasCorrect) Color(0xFF10B981) else Color(0xFFF43F5E),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Simulate Next Scenario", fontWeight = FontWeight.Bold)
                    }
                }
            } else {
                // Choice Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { onMakeChoice(0) },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .testTag("sim_buy_btn"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF10B981), // Genuine Emerald Bull Buy button
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Buy", tint = Color.White)
                            Text("BUY / LONG", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        }
                    }

                    Button(
                        onClick = { onMakeChoice(1) },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .testTag("sim_sell_btn"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF43F5E), // Genuine Rose Bear Sell button
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Sell", tint = Color.White)
                            Text("SELL / SHORT", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        }
                    }

                    Button(
                        onClick = { onMakeChoice(2) },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .testTag("sim_range_btn"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFF0F172A)
                        ),
                        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Range", tint = Color(0xFF0F172A))
                            Text("SIDEWAYS", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}
