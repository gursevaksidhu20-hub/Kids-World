package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.VolumeUp
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
import com.example.ui.components.FlipCard3D
import com.example.ui.components.bounceClick
import com.example.ui.components.floating3D
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.KidsRepository
import com.example.model.AlphabetItem
import com.example.ui.components.CelebrationDialog
import com.example.ui.components.KidsTopBar
import com.example.ui.components.VoiceButton
import com.example.ui.theme.DarkNavy
import com.example.ui.theme.DeepSkyBlue
import com.example.ui.theme.ForestMint
import com.example.ui.theme.LightBg
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.SoftNavy
import com.example.ui.theme.SunnyYellow
import com.example.ui.theme.WarmGold

@Composable
fun AlphabetScreen(
    stars: Int,
    onBack: () -> Unit,
    onSpeak: (String) -> Unit,
    onRecordLetter: (Char) -> Unit,
    onGoToTracing: (Char) -> Unit,
    modifier: Modifier = Modifier
) {
    val alphabets = remember { KidsRepository.alphabets }
    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Explore, 1: Letter Quiz

    val currentItem = alphabets[currentIndex]
    val listState = rememberLazyListState()

    // Speak upon changing letter in Explore tab
    LaunchedEffect(currentIndex, selectedTab) {
        if (selectedTab == 0) {
            onRecordLetter(currentItem.letter)
        }
    }

    LaunchedEffect(currentIndex) {
        listState.animateScrollToItem(currentIndex)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightBg)
    ) {
        KidsTopBar(
            title = "ABC Learning 🔤",
            stars = stars,
            onBack = onBack,
            onSpeakTitle = {
                onSpeak("${currentItem.letter} is for ${currentItem.word}")
            }
        )

        // Tab Selector (Explore vs Quiz)
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.White,
            contentColor = DeepSkyBlue,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp).clip(RoundedCornerShape(14.dp))
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Learn Letters", fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Letter Quiz ⭐", fontWeight = FontWeight.Bold) }
            )
        }

        if (selectedTab == 0) {
            // Horizontal Letter Selector Ribbon
            LazyRow(
                state = listState,
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                itemsIndexed(alphabets) { index, item ->
                    val isSelected = index == currentIndex
                    Box(
                        modifier = Modifier
                            .testTag("alphabet_letter_chip_${item.letter}")
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(
                                if (isSelected) DeepSkyBlue else Color.White
                            )
                            .border(
                                1.5.dp,
                                if (isSelected) DeepSkyBlue else Color(0xFFE2E8F0),
                                CircleShape
                            )
                            .clickable {
                                currentIndex = index
                                onSpeak("${item.letter}. ${item.word}.")
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item.letter.toString(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = if (isSelected) Color.White else DarkNavy
                        )
                    }
                }
            }

            // Big 3D Flashcard with Realistic Y-Axis Flip & Floating Mascot
            var isCardFlipped by remember { mutableStateOf(false) }
            LaunchedEffect(currentIndex) { isCardFlipped = false }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                FlipCard3D(
                    isFlipped = isCardFlipped,
                    onFlip = {
                        isCardFlipped = !isCardFlipped
                        onSpeak(if (isCardFlipped) "Flipped card to see Hindi and fun facts!" else "Flipped back to letter ${currentItem.letter}!")
                    },
                    modifier = Modifier.fillMaxSize(),
                    frontContent = {
                        Card(
                            shape = RoundedCornerShape(28.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                            modifier = Modifier
                                .fillMaxSize()
                                .shadow(8.dp, RoundedCornerShape(28.dp))
                                .border(2.5.dp, Color(0xFFEDF2F9), RoundedCornerShape(28.dp))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(18.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Top Letter Bar: Uppercase & Lowercase
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "${currentItem.letter}${currentItem.letter.lowercaseChar()}",
                                            fontSize = 46.sp,
                                            fontWeight = FontWeight.Black,
                                            color = DeepSkyBlue
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(Color(0xFFFFF9DB))
                                                .padding(horizontal = 10.dp, vertical = 4.dp)
                                        ) {
                                            Text(
                                                text = "/${currentItem.phonics}/",
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color(0xFF805B00)
                                            )
                                        }
                                    }

                                    VoiceButton(
                                        onSpeak = {
                                            onSpeak("${currentItem.letter} for ${currentItem.word}. Phonics: ${currentItem.phonics}.")
                                        },
                                        size = 46
                                    )
                                }

                                // 3D Floating Colorful Emoji Illustration
                                Box(
                                    modifier = Modifier
                                        .floating3D(verticalOffset = 6f, rotationDeg = 3f)
                                        .size(136.dp)
                                        .clip(CircleShape)
                                        .background(
                                            Brush.radialGradient(
                                                listOf(Color(0xFFE8F4FD), Color(0xFFD0ECFD))
                                            )
                                        )
                                        .shadow(8.dp, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = currentItem.emoji,
                                        fontSize = 78.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }

                                // English Word & Hindi Meaning
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = currentItem.word,
                                        style = MaterialTheme.typography.displaySmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = DarkNavy
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = currentItem.hindiMeaning,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            color = ForestMint,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }

                                // 3D Flip Hint Pill
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(Color(0xFFEBF5FF))
                                        .border(1.dp, DeepSkyBlue.copy(alpha = 0.3f), RoundedCornerShape(14.dp))
                                        .padding(horizontal = 14.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = "🔄 Tap Card to Flip in 3D!",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = DeepSkyBlue
                                    )
                                }

                                // Action button: Finger Trace this letter
                                Button(
                                    onClick = { onGoToTracing(currentItem.letter) },
                                    colors = ButtonDefaults.buttonColors(containerColor = ForestMint),
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("alphabet_trace_shortcut_button")
                                ) {
                                    Icon(Icons.Default.Create, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Trace Letter ${currentItem.letter} With Finger ✍️", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    },
                    backContent = {
                        // 3D Reverse Side with Hindi Breakdown & Fun Fact
                        Card(
                            shape = RoundedCornerShape(28.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDF5)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                            modifier = Modifier
                                .fillMaxSize()
                                .shadow(8.dp, RoundedCornerShape(28.dp))
                                .border(2.5.dp, Color(0xFFFFD85E), RoundedCornerShape(28.dp))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Hindi & Details 🇮🇳",
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFB45309),
                                        fontSize = 14.sp
                                    )
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(Color(0xFFFEF3C7))
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text("Tap to Flip Back 🔄", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = DarkNavy)
                                    }
                                }

                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = currentItem.emoji, fontSize = 54.sp)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = currentItem.hindiMeaning,
                                        style = MaterialTheme.typography.headlineMedium.copy(
                                            color = DarkNavy,
                                            fontWeight = FontWeight.Black
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Example Sentence:",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SoftNavy
                                    )
                                    Text(
                                        text = "\"${currentItem.exampleSentence}\"",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = DarkNavy,
                                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                        ),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(horizontal = 10.dp)
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(Color(0xFFF1F5F9))
                                        .padding(12.dp)
                                ) {
                                    Text(
                                        text = "💡 Fun Fact: ${currentItem.funFact}",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontSize = 12.sp,
                                            color = DarkNavy
                                        ),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }

                                Button(
                                    onClick = {
                                        onSpeak("${currentItem.word} means ${currentItem.hindiMeaning}. ${currentItem.funFact}")
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = DeepSkyBlue),
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(Icons.Default.VolumeUp, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Listen in English & Hindi 🔊", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                )
            }

            // Previous and Next Nav Controls
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = {
                        if (currentIndex > 0) {
                            currentIndex--
                            val item = alphabets[currentIndex]
                            onSpeak("${item.letter}. ${item.word}.")
                        }
                    },
                    enabled = currentIndex > 0,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.testTag("alphabet_prev_button")
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Previous", fontWeight = FontWeight.Bold)
                }

                Text(
                    text = "${currentIndex + 1} of 26",
                    fontWeight = FontWeight.Bold,
                    color = SoftNavy
                )

                Button(
                    onClick = {
                        if (currentIndex < alphabets.size - 1) {
                            currentIndex++
                            val item = alphabets[currentIndex]
                            onSpeak("${item.letter}. ${item.word}.")
                        }
                    },
                    enabled = currentIndex < alphabets.size - 1,
                    colors = ButtonDefaults.buttonColors(containerColor = DeepSkyBlue),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.testTag("alphabet_next_button")
                ) {
                    Text("Next", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next")
                }
            }
        } else {
            // Letter Quiz Mode
            AlphabetQuizView(
                onSpeak = onSpeak,
                onQuizComplete = { /* bonus handled */ }
            )
        }
    }
}

@Composable
fun AlphabetQuizView(
    onSpeak: (String) -> Unit,
    onQuizComplete: (Boolean) -> Unit
) {
    val alphabets = remember { KidsRepository.alphabets }
    var questionNum by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableIntStateOf(-1) }
    var showDialog by remember { mutableStateOf(false) }

    val currentTarget = alphabets[questionNum % alphabets.size]
    val options = remember(questionNum) {
        val wrong = alphabets.filter { it.letter != currentTarget.letter }.shuffled().take(3)
        (wrong + currentTarget).shuffled()
    }

    LaunchedEffect(questionNum) {
        onSpeak("Can you find the letter for ${currentTarget.word}?")
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
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Which letter starts this word?",
                    style = MaterialTheme.typography.titleLarge,
                    color = DarkNavy,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text(text = currentTarget.emoji, fontSize = 72.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = currentTarget.word,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepSkyBlue
                )
                Text(
                    text = currentTarget.hindiMeaning,
                    fontSize = 16.sp,
                    color = SoftNavy
                )
            }
        }

        // 4 Large answer buttons in a 2x2 grid
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuizLetterOption(
                    letter = options[0].letter,
                    isCorrect = options[0].letter == currentTarget.letter,
                    isSelected = selectedOption == 0,
                    onClick = {
                        selectedOption = 0
                        val isRight = options[0].letter == currentTarget.letter
                        if (isRight) {
                            onSpeak("Great job! ${options[0].letter} is correct!")
                            showDialog = true
                        } else {
                            onSpeak("Try again! You can do it!")
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
                QuizLetterOption(
                    letter = options[1].letter,
                    isCorrect = options[1].letter == currentTarget.letter,
                    isSelected = selectedOption == 1,
                    onClick = {
                        selectedOption = 1
                        val isRight = options[1].letter == currentTarget.letter
                        if (isRight) {
                            onSpeak("Super! ${options[1].letter} is correct!")
                            showDialog = true
                        } else {
                            onSpeak("Try again! You can do it!")
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuizLetterOption(
                    letter = options[2].letter,
                    isCorrect = options[2].letter == currentTarget.letter,
                    isSelected = selectedOption == 2,
                    onClick = {
                        selectedOption = 2
                        val isRight = options[2].letter == currentTarget.letter
                        if (isRight) {
                            onSpeak("Awesome! ${options[2].letter} is correct!")
                            showDialog = true
                        } else {
                            onSpeak("Try again! You can do it!")
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
                QuizLetterOption(
                    letter = options[3].letter,
                    isCorrect = options[3].letter == currentTarget.letter,
                    isSelected = selectedOption == 3,
                    onClick = {
                        selectedOption = 3
                        val isRight = options[3].letter == currentTarget.letter
                        if (isRight) {
                            onSpeak("Wonderful! ${options[3].letter} is correct!")
                            showDialog = true
                        } else {
                            onSpeak("Try again! You can do it!")
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        if (showDialog) {
            CelebrationDialog(
                title = "Brilliant!",
                message = "${currentTarget.letter} is for ${currentTarget.word}! You earned 2 stars! ⭐",
                starsEarned = 2,
                onDismiss = {
                    showDialog = false
                    selectedOption = -1
                    questionNum++
                }
            )
        }
    }
}

@Composable
private fun QuizLetterOption(
    letter: Char,
    isCorrect: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor = when {
        isSelected && isCorrect -> Color(0xFFD4EDDA)
        isSelected && !isCorrect -> Color(0xFFF8D7DA)
        else -> Color.White
    }
    val borderColor = when {
        isSelected && isCorrect -> ForestMint
        isSelected && !isCorrect -> Color.Red
        else -> Color(0xFFCBD5E1)
    }

    Box(
        modifier = modifier
            .testTag("quiz_option_$letter")
            .height(76.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(bgColor)
            .border(2.dp, borderColor, RoundedCornerShape(20.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter.toString(),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = DarkNavy
        )
    }
}
