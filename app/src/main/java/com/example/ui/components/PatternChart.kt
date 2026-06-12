package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PatternChart(
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
            val centerY = height / 2f

            // 1. Draw helper grid background lines
            for (i in 1..3) {
                val y = height * (i / 4f)
                drawLine(
                    color = Color(0xFFE2E8F0),
                    start = Offset(0f, y),
                    end = Offset(width, y),
                    strokeWidth = 1.dp.toPx()
                )
            }

            // Path for the price lines
            val path = Path()

            when (patternType) {
                "support_resistance" -> {
                    // Two major boundaries, path bouncing within
                    val rLineY = height * 0.25f
                    val sLineY = height * 0.75f

                    // Draw Resistance Ceiling (Dotted Red)
                    drawLine(
                        color = RedCandle,
                        start = Offset(0f, rLineY),
                        end = Offset(width, rLineY),
                        strokeWidth = 2.dp.toPx()
                    )
                    // Draw Support Floor (Dotted Green)
                    drawLine(
                        color = GreenCandle,
                        start = Offset(0f, sLineY),
                        end = Offset(width, sLineY),
                        strokeWidth = 2.dp.toPx()
                    )

                    // Bouncing stock curve
                    path.moveTo(0f, centerY)
                    path.lineTo(width * 0.2f, rLineY)
                    path.lineTo(width * 0.4f, sLineY)
                    path.lineTo(width * 0.6f, rLineY)
                    path.lineTo(width * 0.8f, sLineY)
                    path.lineTo(width, rLineY)

                    drawPath(
                        path = path,
                        color = Color.White,
                        style = Stroke(width = 2.5.dp.toPx())
                    )
                }

                "ascending_triangle" -> {
                    val rY = height * 0.25f
                    val startY = height * 0.85f

                    // Flat Resistance Ceiling
                    drawLine(color = RedCandle, start = Offset(width * 0.1f, rY), end = Offset(width * 0.9f, rY), strokeWidth = 2.dp.toPx())
                    // Rising Trendline
                    drawLine(color = GreenCandle, start = Offset(width * 0.1f, startY), end = Offset(width * 0.9f, rY), strokeWidth = 2.dp.toPx())

                    // Oscillating wedge price action
                    path.moveTo(width * 0.1f, startY)
                    path.lineTo(width * 0.25f, rY)
                    path.lineTo(width * 0.4f, height * 0.7f)
                    path.lineTo(width * 0.55f, rY)
                    path.lineTo(width * 0.7f, height * 0.5f)
                    path.lineTo(width * 0.8f, rY)
                    // Breakout arrow upward
                    path.lineTo(width * 0.9f, height * 0.1f)

                    drawPath(path = path, color = Color.White, style = Stroke(width = 2.5.dp.toPx()))

                    // Draw indicator target circle
                    drawCircle(color = GreenCandle, center = Offset(width * 0.9f, height * 0.1f), radius = 5.dp.toPx())
                }

                "descending_triangle" -> {
                    val sY = height * 0.75f
                    val startY = height * 0.15f

                    // Flat Support Floor
                    drawLine(color = GreenCandle, start = Offset(width * 0.1f, sY), end = Offset(width * 0.9f, sY), strokeWidth = 2.dp.toPx())
                    // Falling Trendline
                    drawLine(color = RedCandle, start = Offset(width * 0.1f, startY), end = Offset(width * 0.9f, sY), strokeWidth = 2.dp.toPx())

                    // Oscillating wedge price action
                    path.moveTo(width * 0.1f, startY)
                    path.lineTo(width * 0.25f, sY)
                    path.lineTo(width * 0.4f, height * 0.3f)
                    path.lineTo(width * 0.55f, sY)
                    path.lineTo(width * 0.7f, height * 0.5f)
                    path.lineTo(width * 0.8f, sY)
                    // Breakdown arrow downward
                    path.lineTo(width * 0.9f, height * 0.9f)

                    drawPath(path = path, color = Color.White, style = Stroke(width = 2.5.dp.toPx()))
                    drawCircle(color = RedCandle, center = Offset(width * 0.9f, height * 0.9f), radius = 5.dp.toPx())
                }

                "double_bottom" -> {
                    // W pattern
                    val startY = height * 0.25f
                    val b1Y = height * 0.75f
                    val peakY = height * 0.45f
                    val b2Y = height * 0.75f

                    // Draw Neckline
                    drawLine(color = Color(0x60FFFFFF), start = Offset(0f, peakY), end = Offset(width, peakY), strokeWidth = 1.5.dp.toPx())

                    path.moveTo(width * 0.1f, startY)
                    path.lineTo(width * 0.3f, b1Y) // Bottom 1
                    path.lineTo(width * 0.5f, peakY) // Peak central
                    path.lineTo(width * 0.7f, b2Y) // Bottom 2
                    path.lineTo(width * 0.85f, peakY) // Back to Neckline
                    path.lineTo(width * 0.95f, height * 0.15f) // Strong bullish breakout drive

                    drawPath(path = path, color = Color.White, style = Stroke(width = 3.dp.toPx()))

                    // Circle highlighting double bottoms
                    drawCircle(color = GreenCandle, center = Offset(width * 0.3f, b1Y), radius = 6.dp.toPx(), style = Stroke(2.dp.toPx()))
                    drawCircle(color = GreenCandle, center = Offset(width * 0.7f, b2Y), radius = 6.dp.toPx(), style = Stroke(2.dp.toPx()))
                }

                "double_top" -> {
                    // M pattern
                    val startY = height * 0.75f
                    val t1Y = height * 0.25f
                    val valleyY = height * 0.55f
                    val t2Y = height * 0.25f

                    // Neckline support
                    drawLine(color = Color(0x60FFFFFF), start = Offset(0f, valleyY), end = Offset(width, valleyY), strokeWidth = 1.5.dp.toPx())

                    path.moveTo(width * 0.1f, startY)
                    path.lineTo(width * 0.3f, t1Y) // Peak 1
                    path.lineTo(width * 0.5f, valleyY) // Valley
                    path.lineTo(width * 0.7f, t2Y) // Peak 2
                    path.lineTo(width * 0.85f, valleyY) // Reconnect neck
                    path.lineTo(width * 0.95f, height * 0.85f) // Bearish plunge

                    drawPath(path = path, color = Color.White, style = Stroke(width = 3.dp.toPx()))

                    drawCircle(color = RedCandle, center = Offset(width * 0.3f, t1Y), radius = 6.dp.toPx(), style = Stroke(2.dp.toPx()))
                    drawCircle(color = RedCandle, center = Offset(width * 0.7f, t2Y), radius = 6.dp.toPx(), style = Stroke(2.dp.toPx()))
                }

                "head_shoulders" -> {
                    // Left shoulder, Head, Right shoulder
                    val sLine = height * 0.7f
                    val shY = height * 0.4f
                    val hY = height * 0.15f

                    // Draw diagonal/horizontal neckline
                    drawLine(color = Color(0x80EF5350), start = Offset(width * 0.05f, sLine), end = Offset(width * 0.95f, sLine), strokeWidth = 2.dp.toPx())

                    path.moveTo(width * 0.05f, height * 0.55f)
                    path.lineTo(width * 0.2f, shY) // Left Shoulder
                    path.lineTo(width * 0.35f, sLine) // Trough 1
                    path.lineTo(width * 0.5f, hY) // Head
                    path.lineTo(width * 0.65f, sLine) // Trough 2
                    path.lineTo(width * 0.8f, shY) // Right Shoulder
                    path.lineTo(width * 0.88f, sLine) // Neckline break
                    path.lineTo(width * 0.95f, height * 0.95f) // Massive target crash

                    drawPath(path = path, color = Color.White, style = Stroke(width = 2.5.dp.toPx()))
                }

                "inverse_head_shoulders" -> {
                    // Reverse H&S
                    val sLine = height * 0.3f
                    val shY = height * 0.6f
                    val hY = height * 0.85f

                    // Draw diagonal neckline (resistance ceiling)
                    drawLine(color = Color(0x8026A69A), start = Offset(width * 0.05f, sLine), end = Offset(width * 0.95f, sLine), strokeWidth = 2.dp.toPx())

                    path.moveTo(width * 0.05f, height * 0.45f)
                    path.lineTo(width * 0.2f, shY) // Left shoulder bottom
                    path.lineTo(width * 0.35f, sLine) // Intermediate peak
                    path.lineTo(width * 0.5f, hY) // Deep Head bottom
                    path.lineTo(width * 0.65f, sLine) // Intermediate peak
                    path.lineTo(width * 0.8f, shY) // Right shoulder bottom
                    path.lineTo(width * 0.88f, sLine) // Breakout point
                    path.lineTo(width * 0.95f, height * 0.05f) // Huge bullish expansion

                    drawPath(path = path, color = Color.White, style = Stroke(width = 2.5.dp.toPx()))
                }

                "bullish_pennant" -> {
                    // Pole and compact triangle
                    val poleBaseX = width * 0.15f
                    val poleBaseY = height * 0.9f
                    val poleTopY = height * 0.2f

                    // Draw Pole
                    drawLine(color = GreenCandle, start = Offset(poleBaseX, poleBaseY), end = Offset(poleBaseX, poleTopY), strokeWidth = 4.dp.toPx())

                    // Draw Triangle converging bounds
                    val pStartX = width * 0.15f
                    val pEndX = width * 0.6f
                    drawLine(color = Color.LightGray, start = Offset(pStartX, poleTopY), end = Offset(pEndX, height * 0.35f), strokeWidth = 1.5.dp.toPx())
                    drawLine(color = Color.LightGray, start = Offset(pStartX, height * 0.55f), end = Offset(pEndX, height * 0.35f), strokeWidth = 1.5.dp.toPx())

                    // Consolidation prices inside
                    path.moveTo(poleBaseX, poleTopY)
                    path.lineTo(width * 0.28f, height * 0.52f)
                    path.lineTo(width * 0.4f, height * 0.28f)
                    path.lineTo(width * 0.5f, height * 0.42f)
                    path.lineTo(pEndX, height * 0.35f)
                    // Massive breakout surge
                    path.lineTo(width * 0.85f, height * 0.1f)

                    drawPath(path = path, color = Color.White, style = Stroke(width = 2.5.dp.toPx()))
                }

                "cup_handle" -> {
                    // Rounded bowl shape + small flag handle
                    path.moveTo(width * 0.1f, height * 0.25f)
                    // Quadratic Bezier relative to center bottom
                    path.quadraticTo(
                        width * 0.4f, height * 0.95f, // Control
                        width * 0.7f, height * 0.25f  // End of cup rim
                    )

                    // Draw Handle
                    path.lineTo(width * 0.78f, height * 0.45f)
                    path.lineTo(width * 0.84f, height * 0.3f)
                    // Breakout upward
                    path.lineTo(width * 0.95f, height * 0.1f)

                    drawPath(path = path, color = Color.White, style = Stroke(width = 2.5.dp.toPx()))

                    // Visual trigger line
                    drawLine(color = GreenCandle, start = Offset(width * 0.1f, height * 0.25f), end = Offset(width * 0.7f, height * 0.25f), strokeWidth = 1.dp.toPx())
                }

                else -> {
                    // Generic price wave fallback
                    path.moveTo(0f, centerY)
                    for (i in 1..10) {
                        val x = width * (i / 10f)
                        val y = centerY + (if (i % 2 == 0) 30.dp.toPx() else -30.dp.toPx())
                        path.lineTo(x, y)
                    }
                    drawPath(path = path, color = Color.White, style = Stroke(width = 2.5.dp.toPx()))
                }
            }
        }

        // Overlay with confirmation signal
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .background(Color(0xE01C2030), shape = RoundedCornerShape(4.dp))
                .padding(6.dp)
        ) {
            val label = when (patternType) {
                "support_resistance" -> "Key Swing Pivot Ranges"
                "ascending_triangle", "bullish_pennant", "cup_handle" -> "Explosive BULLISH bias"
                "descending_triangle", "double_top", "head_shoulders" -> "Major BEARISH break bias"
                "double_bottom", "inverse_head_shoulders" -> "Market Accumulation Bottoms"
                else -> "Technical Chart Schematic"
            }
            Text(
                text = label,
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
