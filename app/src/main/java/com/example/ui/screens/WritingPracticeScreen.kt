package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.geometry.Offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DrawingStroke
import com.example.ui.components.CelebrationDialog
import com.example.ui.components.KidsTopBar
import com.example.ui.theme.DarkNavy
import com.example.ui.theme.DeepSkyBlue
import com.example.ui.theme.ForestMint
import com.example.ui.theme.LightBg
import com.example.ui.theme.SoftNavy
import com.example.ui.theme.SunnyYellow

@Composable
fun WritingPracticeScreen(
    stars: Int,
    initialTarget: String = "A",
    onBack: () -> Unit,
    onSpeak: (String) -> Unit,
    onRecordPractice: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Practice categories: Uppercase, Lowercase, Numbers, Shapes
    val uppercaseLetters = remember { ('A'..'Z').map { it.toString() } }
    val lowercaseLetters = remember { ('a'..'z').map { it.toString() } }
    val numbersList = remember { (0..9).map { it.toString() } }
    val shapesList = remember { listOf("⭕ Circle", "⬛ Square", "🔺 Triangle", "⭐ Star", "❤️ Heart") }

    var selectedCategory by remember { mutableIntStateOf(0) } // 0: Uppercase, 1: Lowercase, 2: Numbers, 3: Shapes
    var currentSymbol by remember { mutableStateOf(initialTarget) }

    val currentList = remember(selectedCategory) {
        when (selectedCategory) {
            0 -> uppercaseLetters
            1 -> lowercaseLetters
            2 -> numbersList
            else -> shapesList
        }
    }

    // User strokes
    val strokes = remember { mutableStateListOf<DrawingStroke>() }
    var currentPoints by remember { mutableStateOf<List<Pair<Float, Float>>>(emptyList()) }
    var showCelebration by remember { mutableStateOf(false) }

    LaunchedEffect(currentSymbol) {
        strokes.clear()
        onSpeak("Trace $currentSymbol with your finger!")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightBg)
    ) {
        KidsTopBar(
            title = "Writing Practice ✍️",
            stars = stars,
            onBack = onBack,
            onSpeakTitle = { onSpeak("Writing Practice. Trace $currentSymbol on the dotted line!") }
        )

        // Category Tabs
        TabRow(
            selectedTabIndex = selectedCategory,
            containerColor = Color.White,
            contentColor = DeepSkyBlue,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp).clip(RoundedCornerShape(14.dp))
        ) {
            Tab(
                selected = selectedCategory == 0,
                onClick = {
                    selectedCategory = 0
                    currentSymbol = uppercaseLetters[0]
                },
                text = { Text("A–Z", fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = selectedCategory == 1,
                onClick = {
                    selectedCategory = 1
                    currentSymbol = lowercaseLetters[0]
                },
                text = { Text("a–z", fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = selectedCategory == 2,
                onClick = {
                    selectedCategory = 2
                    currentSymbol = numbersList[0]
                },
                text = { Text("0–9", fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = selectedCategory == 3,
                onClick = {
                    selectedCategory = 3
                    currentSymbol = shapesList[0]
                },
                text = { Text("Shapes", fontWeight = FontWeight.Bold) }
            )
        }

        // Horizontal Selection Ribbon
        LazyRow(
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(currentList) { item ->
                val isSelected = item == currentSymbol
                Box(
                    modifier = Modifier
                        .testTag("tracing_chip_$item")
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) DeepSkyBlue else Color.White)
                        .border(1.5.dp, if (isSelected) DeepSkyBlue else Color(0xFFE2E8F0), CircleShape)
                        .clickable { currentSymbol = item },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.take(1),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = if (isSelected) Color.White else DarkNavy
                    )
                }
            }
        }

        // Tracing Canvas Card
        Card(
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 6.dp)
                .shadow(4.dp, RoundedCornerShape(26.dp))
                .border(2.dp, Color(0xFFEDF2F9), RoundedCornerShape(26.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(currentSymbol) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                currentPoints = listOf(offset.x to offset.y)
                            },
                            onDrag = { change, _ ->
                                change.consume()
                                currentPoints = currentPoints + (change.position.x to change.position.y)
                            },
                            onDragEnd = {
                                if (currentPoints.isNotEmpty()) {
                                    strokes.add(
                                        DrawingStroke(
                                            points = currentPoints,
                                            color = 0xFF4D96FF,
                                            strokeWidth = 26f
                                        )
                                    )
                                    currentPoints = emptyList()

                                    // If child drew enough strokes, award success!
                                    if (strokes.size >= 1 && strokes.sumOf { it.points.size } > 20) {
                                        showCelebration = true
                                        onRecordPractice()
                                        onSpeak("Well done! Beautiful tracing!")
                                    }
                                }
                            }
                        )
                    }
            ) {
                // Background Guide Dotted Canvas
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height

                    // 1. Draw ruled lines (top line, dashed middle, baseline)
                    val topY = h * 0.22f
                    val midY = h * 0.50f
                    val botY = h * 0.78f

                    drawLine(Color(0xFFE2E8F0), Offset(0f, topY), Offset(w, topY), strokeWidth = 2f)
                    drawLine(
                        Color(0xFFCBD5E1),
                        Offset(0f, midY),
                        Offset(w, midY),
                        strokeWidth = 2f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 12f))
                    )
                    drawLine(Color(0xFFE2E8F0), Offset(0f, botY), Offset(w, botY), strokeWidth = 2f)

                    // 2. Draw large Dotted Guide Character in center
                    val paint = android.graphics.Paint().apply {
                        textSize = h * 0.55f
                        textAlign = android.graphics.Paint.Align.CENTER
                        color = android.graphics.Color.parseColor("#E2E8F0")
                        typeface = android.graphics.Typeface.DEFAULT_BOLD
                        isAntiAlias = true
                    }
                    val displayChar = currentSymbol.split(" ").firstOrNull() ?: currentSymbol
                    drawContext.canvas.nativeCanvas.drawText(
                        displayChar,
                        w / 2f,
                        h * 0.68f,
                        paint
                    )

                    // 3. User's finger tracing strokes
                    strokes.forEach { stroke ->
                        if (stroke.points.size > 1) {
                            val path = Path()
                            stroke.points.forEachIndexed { i, pt ->
                                if (i == 0) path.moveTo(pt.first, pt.second)
                                else path.lineTo(pt.first, pt.second)
                            }
                            drawPath(
                                path,
                                Color(stroke.color),
                                style = Stroke(width = stroke.strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)
                            )
                        }
                    }

                    // Active drawing
                    if (currentPoints.size > 1) {
                        val activePath = Path()
                        currentPoints.forEachIndexed { i, pt ->
                            if (i == 0) activePath.moveTo(pt.first, pt.second)
                            else activePath.lineTo(pt.first, pt.second)
                        }
                        drawPath(
                            activePath,
                            Color(0xFF4D96FF),
                            style = Stroke(width = 26f, cap = StrokeCap.Round, join = StrokeJoin.Round)
                        )
                    }
                }

                // Helper prompt text at top
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFFFF9DB))
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Follow the dotted lines with your finger! 👆",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF8A6200)
                        )
                    }
                }
            }
        }

        // Bottom Controls: Clear, Skip, Next
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { strokes.clear() },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.testTag("tracing_clear_button")
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Retry 🧼", fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = {
                    val idx = currentList.indexOf(currentSymbol)
                    if (idx < currentList.size - 1) {
                        currentSymbol = currentList[idx + 1]
                    } else {
                        currentSymbol = currentList[0]
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = DeepSkyBlue),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.testTag("tracing_next_button")
            ) {
                Text("Next Symbol", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(6.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
            }
        }
    }

    if (showCelebration) {
        CelebrationDialog(
            title = "Super Writer! ✍️",
            message = "You traced $currentSymbol beautifully! You earned 2 stars! ⭐",
            starsEarned = 2,
            onDismiss = {
                showCelebration = false
                val idx = currentList.indexOf(currentSymbol)
                if (idx < currentList.size - 1) {
                    currentSymbol = currentList[idx + 1]
                }
            }
        )
    }
}
