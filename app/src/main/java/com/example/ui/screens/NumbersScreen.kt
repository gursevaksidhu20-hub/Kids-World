package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import com.example.ui.components.bounceClick
import com.example.ui.components.floating3D
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.KidsRepository
import com.example.model.NumberItem
import com.example.ui.components.CelebrationDialog
import com.example.ui.components.KidsTopBar
import com.example.ui.components.VoiceButton
import com.example.ui.theme.DarkNavy
import com.example.ui.theme.DeepSkyBlue
import com.example.ui.theme.ForestMint
import com.example.ui.theme.LightBg
import com.example.ui.theme.SoftNavy
import com.example.ui.theme.SunnyYellow
import com.example.ui.theme.WarmGold

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NumbersScreen(
    stars: Int,
    onBack: () -> Unit,
    onSpeak: (String) -> Unit,
    onRecordNumber: (Int) -> Unit,
    onGoToTracing: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val numbers = remember { KidsRepository.numbers }
    var selectedNumber by remember { mutableIntStateOf(1) }
    var activeTab by remember { mutableIntStateOf(0) } // 0: Number Card, 1: 1–100 Grid, 2: Math & Puzzles

    val currentItem = numbers[selectedNumber - 1]

    LaunchedEffect(selectedNumber) {
        onRecordNumber(selectedNumber)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightBg)
    ) {
        KidsTopBar(
            title = "Numbers 1–100 🔢",
            stars = stars,
            onBack = onBack,
            onSpeakTitle = {
                onSpeak("Number ${currentItem.value}. ${currentItem.englishWord}.")
            }
        )

        // 3-way Tabs
        TabRow(
            selectedTabIndex = activeTab,
            containerColor = Color.White,
            contentColor = DeepSkyBlue,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp).clip(RoundedCornerShape(14.dp))
        ) {
            Tab(
                selected = activeTab == 0,
                onClick = { activeTab = 0 },
                text = { Text("Number View", fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = activeTab == 1,
                onClick = { activeTab = 1 },
                text = { Text("1–100 Grid", fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = activeTab == 2,
                onClick = { activeTab = 2 },
                text = { Text("Math & Fun", fontWeight = FontWeight.Bold) }
            )
        }

        when (activeTab) {
            0 -> {
                // Large Selected Number View
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Card(
                        shape = RoundedCornerShape(26.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .shadow(4.dp, RoundedCornerShape(26.dp))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(18.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceAround
                        ) {
                            // Top Voice & Category Header
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Color(0xFFEBF5FF))
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = "Group: ${currentItem.category}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = DeepSkyBlue
                                    )
                                }

                                VoiceButton(
                                    onSpeak = {
                                        onSpeak("Number ${currentItem.value}. ${currentItem.englishWord}. In Hindi: ${currentItem.hindiPronunciation}.")
                                    },
                                    size = 46
                                )
                            }

                            // 3D Floating Colorful Number Digit
                            Box(
                                modifier = Modifier
                                    .floating3D(verticalOffset = 6f, rotationDeg = 3f)
                                    .size(118.dp)
                                    .clip(CircleShape)
                                    .background(
                                        Brush.radialGradient(
                                            listOf(Color(0xFFFFF9DB), SunnyYellow.copy(alpha = 0.5f))
                                        )
                                    )
                                    .border(3.5.dp, WarmGold, CircleShape)
                                    .shadow(8.dp, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = currentItem.value.toString(),
                                    fontSize = 58.sp,
                                    fontWeight = FontWeight.Black,
                                    color = DarkNavy
                                )
                            }

                            // English Name & Hindi Name
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = currentItem.englishWord,
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkNavy
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${currentItem.hindiWord} (${currentItem.hindiPronunciation})",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ForestMint
                                )
                            }

                            // Visual Counting Objects for numbers (up to 10)
                            if (currentItem.value <= 10) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "Count with me:",
                                        fontSize = 13.sp,
                                        color = SoftNavy,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    FlowRow(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalArrangement = Arrangement.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        repeat(currentItem.value) {
                                            Text(
                                                text = currentItem.objectEmoji,
                                                fontSize = 28.sp,
                                                modifier = Modifier.padding(3.dp)
                                            )
                                        }
                                    }
                                }
                            } else {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(Color(0xFFF8FAFC))
                                        .padding(10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Great job reaching big numbers! Keep exploring to 100! 🚀",
                                        fontSize = 12.sp,
                                        color = SoftNavy,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }

                            // Trace shortcut if 0-9
                            if (currentItem.value <= 9) {
                                Button(
                                    onClick = { onGoToTracing(currentItem.value) },
                                    colors = ButtonDefaults.buttonColors(containerColor = ForestMint),
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier.fillMaxWidth().testTag("number_trace_button")
                                ) {
                                    Icon(Icons.Default.Create, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Trace Number ${currentItem.value} ✍️", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }

                    // Navigation bar (Prev / Next)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = {
                                if (selectedNumber > 1) {
                                    selectedNumber--
                                    onSpeak("Number $selectedNumber")
                                }
                            },
                            enabled = selectedNumber > 1,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.testTag("number_prev_button")
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Previous", fontWeight = FontWeight.Bold)
                        }

                        Text(
                            text = "$selectedNumber / 100",
                            fontWeight = FontWeight.Bold,
                            color = SoftNavy
                        )

                        Button(
                            onClick = {
                                if (selectedNumber < 100) {
                                    selectedNumber++
                                    onSpeak("Number $selectedNumber")
                                }
                            },
                            enabled = selectedNumber < 100,
                            colors = ButtonDefaults.buttonColors(containerColor = DeepSkyBlue),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.testTag("number_next_button")
                        ) {
                            Text("Next", fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                        }
                    }
                }
            }

            1 -> {
                // 1–100 Interactive Number Grid
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Tap any number from 1 to 100 to hear its name! 👇",
                        fontSize = 13.sp,
                        color = SoftNavy,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(5),
                        contentPadding = PaddingValues(bottom = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(numbers) { num ->
                            val isSelected = num.value == selectedNumber
                            Box(
                                modifier = Modifier
                                    .testTag("number_grid_cell_${num.value}")
                                    .size(54.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(
                                        if (isSelected) WarmGold else Color.White
                                    )
                                    .border(
                                        1.5.dp,
                                        if (isSelected) Color(0xFFCC8800) else Color(0xFFE2E8F0),
                                        RoundedCornerShape(14.dp)
                                    )
                                    .clickable {
                                        selectedNumber = num.value
                                        onSpeak("${num.value}. ${num.englishWord}.")
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = num.value.toString(),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp,
                                    color = if (isSelected) Color.White else DarkNavy
                                )
                            }
                        }
                    }
                }
            }

            2 -> {
                // Simple Math and Missing Number Puzzles
                NumberFunPuzzleView(onSpeak = onSpeak)
            }
        }
    }
}

@Composable
fun NumberFunPuzzleView(onSpeak: (String) -> Unit) {
    var puzzleIndex by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableIntStateOf(-1) }
    var showDialog by remember { mutableStateOf(false) }

    data class NumberPuzzle(
        val question: String,
        val hint: String,
        val options: List<Int>,
        val correct: Int
    )

    val puzzles = remember {
        listOf(
            NumberPuzzle("What number is missing? 2, 4, __, 8", "Count by 2s!", listOf(5, 6, 7, 9), 6),
            NumberPuzzle("How much is 4 + 3 = ?", "Add them up!", listOf(6, 7, 8, 9), 7),
            NumberPuzzle("What comes right after 9?", "Counting forward!", listOf(8, 10, 11, 12), 10),
            NumberPuzzle("How much is 10 - 2 = ?", "Take away 2!", listOf(7, 8, 9, 6), 8),
            NumberPuzzle("What number is missing? 10, 20, __, 40", "Count by 10s!", listOf(25, 30, 35, 50), 30)
        )
    }

    val current = puzzles[puzzleIndex % puzzles.size]

    LaunchedEffect(puzzleIndex) {
        onSpeak(current.question)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Math Puzzle 🧩", fontWeight = FontWeight.Bold, color = ForestMint, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = current.question,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = DarkNavy,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "💡 Hint: ${current.hint}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SoftNavy
                )
            }
        }

        // 4 Large answer buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                current.options.take(2).forEachIndexed { i, opt ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(70.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(if (selectedOption == i && opt == current.correct) Color(0xFFD4EDDA) else Color.White)
                            .border(2.dp, if (selectedOption == i && opt == current.correct) ForestMint else Color(0xFFE2E8F0), RoundedCornerShape(18.dp))
                            .clickable {
                                selectedOption = i
                                if (opt == current.correct) {
                                    onSpeak("Correct! $opt is the right answer!")
                                    showDialog = true
                                } else {
                                    onSpeak("Try again! You can do it!")
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = opt.toString(), fontSize = 28.sp, fontWeight = FontWeight.Bold, color = DarkNavy)
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                current.options.drop(2).forEachIndexed { i, opt ->
                    val actualIndex = i + 2
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(70.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(if (selectedOption == actualIndex && opt == current.correct) Color(0xFFD4EDDA) else Color.White)
                            .border(2.dp, if (selectedOption == actualIndex && opt == current.correct) ForestMint else Color(0xFFE2E8F0), RoundedCornerShape(18.dp))
                            .clickable {
                                selectedOption = actualIndex
                                if (opt == current.correct) {
                                    onSpeak("Correct! $opt is the right answer!")
                                    showDialog = true
                                } else {
                                    onSpeak("Try again! You can do it!")
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = opt.toString(), fontSize = 28.sp, fontWeight = FontWeight.Bold, color = DarkNavy)
                    }
                }
            }
        }

        if (showDialog) {
            CelebrationDialog(
                title = "Awesome Math Whiz!",
                message = "You solved it correctly! You earned 2 stars! ⭐",
                starsEarned = 2,
                onDismiss = {
                    showDialog = false
                    selectedOption = -1
                    puzzleIndex++
                }
            )
        }
    }
}
