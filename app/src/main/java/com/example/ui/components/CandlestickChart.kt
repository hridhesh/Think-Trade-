package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Color definitions matching polished modern light-mode layout
val GreenCandle = Color(0xFF10B981) // Beautiful emerald bull
val RedCandle = Color(0xFFF43F5E)   // Sleek crimson bear
val DarkBackground = Color(0xFFF8FAFC) // Light chart card background
val MutedText = Color(0xFF64748B)   // Technical grid text

@Composable
fun CandlestickChart(
    patternType: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(DarkBackground, shape = RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFFE2E8F0), shape = RoundedCornerShape(12.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val centerX = width / 2f
            val centerY = height / 2f

            // Clean background grid lines (Light slate grid)
            for (i in 1..3) {
                val y = height * (i / 4f)
                drawLine(
                    color = Color(0xFFE2E8F0),
                    start = Offset(0f, y),
                    end = Offset(width, y),
                    strokeWidth = 1.dp.toPx()
                )
            }

            when (patternType) {
                "hammer" -> {
                    // Uptrend candle on left for context, then Hammer in downtrend
                    // Let's just draw one prominent Hammer in the center
                    val candleWidth = 50.dp.toPx()
                    val bodyHeight = 25.dp.toPx()
                    val candleY = height * 0.3f
                    val lowerShadowLength = 100.dp.toPx()

                    // Draw Lower Wick (long shadow)
                    drawLine(
                        color = GreenCandle,
                        start = Offset(centerX, candleY + bodyHeight),
                        end = Offset(centerX, candleY + bodyHeight + lowerShadowLength),
                        strokeWidth = 3.dp.toPx()
                    )
                    // Draw Tiny Upper Wick
                    drawLine(
                        color = GreenCandle,
                        start = Offset(centerX, candleY - 10f),
                        end = Offset(centerX, candleY),
                        strokeWidth = 3.dp.toPx()
                    )
                    // Draw Real Body (Green, at the top)
                    drawRect(
                        color = GreenCandle,
                        topLeft = Offset(centerX - candleWidth / 2f, candleY),
                        size = Size(candleWidth, bodyHeight)
                    )
                }

                "shooting_star" -> {
                    val candleWidth = 50.dp.toPx()
                    val bodyHeight = 25.dp.toPx()
                    val candleY = height * 0.61f
                    val upperShadowLength = 100.dp.toPx()

                    // Draw Upper Wick (long shadow)
                    drawLine(
                        color = RedCandle,
                        start = Offset(centerX, candleY - upperShadowLength),
                        end = Offset(centerX, candleY),
                        strokeWidth = 3.dp.toPx()
                    )
                    // Draw Tiny Lower Wick
                    drawLine(
                        color = RedCandle,
                        start = Offset(centerX, candleY + bodyHeight),
                        end = Offset(centerX, candleY + bodyHeight + 10f),
                        strokeWidth = 3.dp.toPx()
                    )
                    // Draw Real Body (Red, at bottom)
                    drawRect(
                        color = RedCandle,
                        topLeft = Offset(centerX - candleWidth / 2f, candleY),
                        size = Size(candleWidth, bodyHeight)
                    )
                }

                "doji" -> {
                    val candleWidth = 50.dp.toPx()
                    val upperShadowLength = 60.dp.toPx()
                    val lowerShadowLength = 60.dp.toPx()

                    // Draw full continuous wick
                    drawLine(
                        color = Color.White,
                        start = Offset(centerX, centerY - upperShadowLength),
                        end = Offset(centerX, centerY + lowerShadowLength),
                        strokeWidth = 3.dp.toPx()
                    )
                    // Draw ultra thin body (horizontal flat line)
                    drawLine(
                        color = Color.White,
                        start = Offset(centerX - candleWidth / 2f, centerY),
                        end = Offset(centerX + candleWidth / 2f, centerY),
                        strokeWidth = 6.dp.toPx()
                    )
                }

                "bullish_engulfing" -> {
                    // Draw small red candle, then large green candle
                    val c1X = centerX - 40.dp.toPx()
                    val c2X = centerX + 40.dp.toPx()

                    // Candle 1: Red Small
                    val c1Y = centerY - 10.dp.toPx()
                    val c1H = 30.dp.toPx()
                    // Wick 1
                    drawLine(color = RedCandle, start = Offset(c1X, c1Y - 15.dp.toPx()), end = Offset(c1X, c1Y + c1H + 15.dp.toPx()), strokeWidth = 2.dp.toPx())
                    // Body 1
                    drawRect(color = RedCandle, topLeft = Offset(c1X - 15.dp.toPx(), c1Y), size = Size(30.dp.toPx(), c1H))

                    // Candle 2: Green large (engulfs c1 completely)
                    val c2Y = centerY - 35.dp.toPx()
                    val c2H = 80.dp.toPx()
                    // Wick 2
                    drawLine(color = GreenCandle, start = Offset(c2X, c2Y - 15.dp.toPx()), end = Offset(c2X, c2Y + c2H + 15.dp.toPx()), strokeWidth = 3.dp.toPx())
                    // Body 2
                    drawRect(color = GreenCandle, topLeft = Offset(c2X - 22.dp.toPx(), c2Y), size = Size(44.dp.toPx(), c2H))
                }

                "bearish_engulfing" -> {
                    val c1X = centerX - 40.dp.toPx()
                    val c2X = centerX + 40.dp.toPx()

                    // Candle 1: Green Small
                    val c1Y = centerY - 15.dp.toPx()
                    val c1H = 30.dp.toPx()
                    // Wick 1
                    drawLine(color = GreenCandle, start = Offset(c1X, c1Y - 15.dp.toPx()), end = Offset(c1X, c1Y + c1H + 15.dp.toPx()), strokeWidth = 2.dp.toPx())
                    // Body 1
                    drawRect(color = GreenCandle, topLeft = Offset(c1X - 15.dp.toPx(), c1Y), size = Size(30.dp.toPx(), c1H))

                    // Candle 2: Red large (engulfs c1)
                    val c2Y = centerY - 35.dp.toPx()
                    val c2H = 80.dp.toPx()
                    // Wick 2
                    drawLine(color = RedCandle, start = Offset(c2X, c2Y - 15.dp.toPx()), end = Offset(c2X, c2Y + c2H + 15.dp.toPx()), strokeWidth = 3.dp.toPx())
                    // Body 2
                    drawRect(color = RedCandle, topLeft = Offset(c2X - 22.dp.toPx(), c2Y), size = Size(44.dp.toPx(), c2H))
                }

                "morning_star" -> {
                    // Red candle, small star/doji lower, big green candle
                    val c1X = centerX - 60.dp.toPx()
                    val c2X = centerX
                    val c3X = centerX + 60.dp.toPx()

                    // C1: Red Large
                    val c1Y = centerY - 40.dp.toPx()
                    val c1H = 60.dp.toPx()
                    drawLine(color = RedCandle, start = Offset(c1X, c1Y - 10.dp.toPx()), end = Offset(c1X, c1Y + c1H + 10.dp.toPx()), strokeWidth = 2.dp.toPx())
                    drawRect(color = RedCandle, topLeft = Offset(c1X - 15.dp.toPx(), c1Y), size = Size(30.dp.toPx(), c1H))

                    // C2: Star gapped down representation
                    val c2Y = centerY + 30.dp.toPx()
                    val c2H = 15.dp.toPx()
                    drawLine(color = Color.White, start = Offset(c2X, c2Y - 10.dp.toPx()), end = Offset(c2X, c2Y + c2H + 10.dp.toPx()), strokeWidth = 2.dp.toPx())
                    drawRect(color = Color.Gray, topLeft = Offset(c2X - 10.dp.toPx(), c2Y), size = Size(20.dp.toPx(), c2H))

                    // C3: Green Large (recovers deep into C1 range)
                    val c3Y = centerY - 30.dp.toPx()
                    val c3H = 55.dp.toPx()
                    drawLine(color = GreenCandle, start = Offset(c3X, c3Y - 10.dp.toPx()), end = Offset(c3X, c3Y + c3H + 10.dp.toPx()), strokeWidth = 2.dp.toPx())
                    drawRect(color = GreenCandle, topLeft = Offset(c3X - 15.dp.toPx(), c3Y), size = Size(30.dp.toPx(), c3H))
                }

                "evening_star" -> {
                    val c1X = centerX - 60.dp.toPx()
                    val c2X = centerX
                    val c3X = centerX + 60.dp.toPx()

                    // C1: Green Large
                    val c1Y = centerY - 20.dp.toPx()
                    val c1H = 60.dp.toPx()
                    drawLine(color = GreenCandle, start = Offset(c1X, c1Y - 10.dp.toPx()), end = Offset(c1X, c1Y + c1H + 10.dp.toPx()), strokeWidth = 2.dp.toPx())
                    drawRect(color = GreenCandle, topLeft = Offset(c1X - 15.dp.toPx(), c1Y), size = Size(30.dp.toPx(), c1H))

                    // C2: High Gapped Star
                    val c2Y = centerY - 45.dp.toPx()
                    val c2H = 15.dp.toPx()
                    drawLine(color = Color.White, start = Offset(c2X, c2Y - 10.dp.toPx()), end = Offset(c2X, c2Y + c2H + 10.dp.toPx()), strokeWidth = 2.dp.toPx())
                    drawRect(color = Color.Gray, topLeft = Offset(c2X - 10.dp.toPx(), c2Y), size = Size(20.dp.toPx(), c2H))

                    // C3: Red Large closing low
                    val c3Y = centerY - 10.dp.toPx()
                    val c3H = 55.dp.toPx()
                    drawLine(color = RedCandle, start = Offset(c3X, c3Y - 10.dp.toPx()), end = Offset(c3X, c3Y + c3H + 10.dp.toPx()), strokeWidth = 2.dp.toPx())
                    drawRect(color = RedCandle, topLeft = Offset(c3X - 15.dp.toPx(), c3Y), size = Size(30.dp.toPx(), c3H))
                }

                "three_soldiers" -> {
                    // Three consecutive rising green candles
                    val startX = centerX - 60.dp.toPx()
                    for (i in 0..2) {
                        val cX = startX + i * 50.dp.toPx()
                        val cY = centerY + 15.dp.toPx() - (i * 35.dp.toPx())
                        val cH = 50.dp.toPx()

                        drawLine(color = GreenCandle, start = Offset(cX, cY - 10.dp.toPx()), end = Offset(cX, cY + cH + 10.dp.toPx()), strokeWidth = 2.dp.toPx())
                        drawRect(color = GreenCandle, topLeft = Offset(cX - 15.dp.toPx(), cY), size = Size(30.dp.toPx(), cH))
                    }
                }

                "three_crows" -> {
                    // Three consecutive falling red candles
                    val startX = centerX - 60.dp.toPx()
                    for (i in 0..2) {
                        val cX = startX + i * 50.dp.toPx()
                        val cY = centerY - 45.dp.toPx() + (i * 30.dp.toPx())
                        val cH = 50.dp.toPx()

                        drawLine(color = RedCandle, start = Offset(cX, cY - 10.dp.toPx()), end = Offset(cX, cY + cH + 10.dp.toPx()), strokeWidth = 2.dp.toPx())
                        drawRect(color = RedCandle, topLeft = Offset(cX - 15.dp.toPx(), cY), size = Size(30.dp.toPx(), cH))
                    }
                }

                else -> {
                    // Minimal generic candle fallback
                    val cWidth = 40.dp.toPx()
                    val cHeight = 80.dp.toPx()
                    drawLine(color = GreenCandle, start = Offset(centerX, centerY - 60.dp.toPx()), end = Offset(centerX, centerY + 60.dp.toPx()), strokeWidth = 2.dp.toPx())
                    drawRect(color = GreenCandle, topLeft = Offset(centerX - cWidth / 2f, centerY - cHeight / 2f), size = Size(cWidth, cHeight))
                }
            }
        }

        // Informative overlays
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .background(Color(0xE01C2030), shape = RoundedCornerShape(4.dp))
                .padding(6.dp)
        ) {
            val detailText = when (patternType) {
                "hammer" -> "Lower Wick indicates massive price rejection"
                "shooting_star" -> "Upper Shadow indicates top seller resistance"
                "doji" -> "Equality between buyers & sellers"
                "bullish_engulfing", "morning_star", "three_soldiers" -> "Bulls overpowering Bears (Long Signal)"
                "bearish_engulfing", "evening_star", "three_crows" -> "Sellers flooding market (Short Signal)"
                else -> "Interactive Candle Diagram"
            }
            Text(
                text = detailText,
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
