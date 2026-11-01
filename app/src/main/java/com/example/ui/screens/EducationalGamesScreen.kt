package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.KidsQuizData
import com.example.model.QuizQuestion
import com.example.ui.components.CelebrationDialog
import com.example.ui.components.KidsTopBar
import com.example.ui.components.bounceClick
import com.example.ui.components.floating3D
import com.example.ui.components.shake3D
import com.example.ui.theme.DarkNavy
import com.example.ui.theme.DeepSkyBlue
import com.example.ui.theme.ForestMint
import com.example.ui.theme.LightBg
import com.example.ui.theme.SoftNavy

data class MiniGameInfo(
    val type: String,
    val title: String,
    val icon: String,
    val description: String,
    val colorHex: Long
)

@Composable
fun EducationalGamesScreen(
    stars: Int,
    onBack: () -> Unit,
    onSpeak: (String) -> Unit,
    onRecordQuizResult: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val games = remember {
        listOf(
            MiniGameInfo("ALPHABET", "Alphabet Quiz", "🔤", "Match letters to words & sounds", 0xFFFF9A9E),
            MiniGameInfo("NUMBER", "Number Quiz", "🔢", "Math addition & sequence challenge", 0xFFFFD166),
            MiniGameInfo("ANIMAL", "Animal Quiz", "🦁", "Guess animal names & facts", 0xFFFFB347),
            MiniGameInfo("COLOR", "Color Matching", "🌈", "Identify beautiful rainbow colors", 0xFFC8B6FF),
            MiniGameInfo("PICTURE", "Picture Matching", "🖼️", "Match objects to words", 0xFF84DCC6),
            MiniGameInfo("MISSING_LETTER", "Missing Letter", "🧩", "Fill in missing letters in words", 0xFFFF8FA3),
            MiniGameInfo("COUNTING", "Counting Game", "🍎", "Count objects and find the total", 0xFF8FE3B0),
            MiniGameInfo("SHAPE", "Shape Matching", "⭐", "Identify circles, squares & triangles", 0xFF70C8FF)
        )
    }

    var activeGameType by remember { mutableStateOf<String?>(null) }

    if (activeGameType != null) {
        // Active Game Quiz Session
        ActiveQuizSessionView(
            gameType = activeGameType!!,
            stars = stars,
            onBackToMenu = { activeGameType = null },
            onSpeak = onSpeak,
            onRecordQuizResult = onRecordQuizResult
        )
    } else {
        // Games Menu
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(LightBg)
        ) {
            KidsTopBar(
                title = "Educational Games 🎮",
                stars = stars,
                onBack = onBack,
                onSpeakTitle = { onSpeak("Educational Games! Pick a game to play and earn stars!") }
            )

            Text(
                text = "Choose a mini-game to play:",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = SoftNavy,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(14.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(games) { game ->
                    Card(
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier
                            .testTag("game_card_${game.type.lowercase()}")
                            .fillMaxWidth()
                            .clickable {
                                activeGameType = game.type
                                onSpeak("Starting ${game.title}!")
                            }
                            .border(1.5.dp, Color(0xFFEDF2F9), RoundedCornerShape(22.dp))
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(18.dp))
                                    .background(Color(game.colorHex).copy(alpha = 0.35f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = game.icon, fontSize = 32.sp)
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = game.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = DarkNavy,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = game.description,
                                fontSize = 11.sp,
                                color = SoftNavy,
                                textAlign = TextAlign.Center,
                                maxLines = 2
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ActiveQuizSessionView(
    gameType: String,
    stars: Int,
    onBackToMenu: () -> Unit,
    onSpeak: (String) -> Unit,
    onRecordQuizResult: (Boolean) -> Unit
) {
    val questions = remember(gameType) { KidsQuizData.getQuestionsForGame(gameType) }
    var qIndex by remember { mutableIntStateOf(0) }
    var selectedChoice by remember { mutableIntStateOf(-1) }
    var showCelebration by remember { mutableStateOf(false) }

    val currentQ = questions[qIndex % questions.size]

    LaunchedEffect(qIndex) {
        onSpeak(currentQ.prompt)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBg)
    ) {
        KidsTopBar(
            title = "Quiz Mode 🎮",
            stars = stars,
            onBack = onBackToMenu,
            onSpeakTitle = { onSpeak(currentQ.prompt) }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            // Question Prompt Card
            Card(
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(26.dp))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Question ${qIndex + 1} of ${questions.size}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = ForestMint
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier.floating3D(verticalOffset = 5f, rotationDeg = 3f)
                    ) {
                        Text(text = currentQ.visualHint, fontSize = 72.sp)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = currentQ.prompt,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = DarkNavy,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // 3D Options Buttons with tactile feedback
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                currentQ.options.forEachIndexed { index, optionText ->
                    val isCorrect = index == currentQ.correctIndex
                    val isSelected = selectedChoice == index
                    val bg = when {
                        isSelected && isCorrect -> Color(0xFFD4EDDA)
                        isSelected && !isCorrect -> Color(0xFFF8D7DA)
                        else -> Color.White
                    }
                    val border = when {
                        isSelected && isCorrect -> ForestMint
                        isSelected && !isCorrect -> Color.Red
                        else -> Color(0xFFCBD5E1)
                    }

                    Box(
                        modifier = Modifier
                            .testTag("quiz_choice_$index")
                            .fillMaxWidth()
                            .height(64.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(bg)
                            .border(2.dp, border, RoundedCornerShape(20.dp))
                            .shadow(if (isSelected) 2.dp else 4.dp, RoundedCornerShape(20.dp))
                            .shake3D(trigger = isSelected && !isCorrect)
                            .bounceClick {
                                selectedChoice = index
                                if (isCorrect) {
                                    onRecordQuizResult(true)
                                    onSpeak(currentQ.encouragement)
                                    showCelebration = true
                                } else {
                                    onRecordQuizResult(false)
                                    onSpeak("Good try! Think again and tap another answer!")
                                }
                            }
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = optionText,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkNavy,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            if (showCelebration) {
                CelebrationDialog(
                    title = "Awesome Star! ⭐",
                    message = "${currentQ.encouragement} You earned 2 stars! ⭐",
                    starsEarned = 2,
                    onDismiss = {
                        showCelebration = false
                        selectedChoice = -1
                        qIndex++
                    }
                )
            }
        }
    }
}
