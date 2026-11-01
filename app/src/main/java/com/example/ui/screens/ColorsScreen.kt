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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.KidsRepository
import com.example.model.ColorItem
import com.example.ui.components.CelebrationDialog
import com.example.ui.components.ConfettiCelebrationOverlay
import com.example.ui.components.KidsTopBar
import com.example.ui.components.VoiceButton
import com.example.ui.components.bounceClick
import com.example.ui.components.floating3D
import com.example.ui.theme.DarkNavy
import com.example.ui.theme.DeepSkyBlue
import com.example.ui.theme.ForestMint
import com.example.ui.theme.LightBg
import com.example.ui.theme.SoftNavy

@Composable
fun ColorsScreen(
    stars: Int,
    onBack: () -> Unit,
    onSpeak: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = remember { KidsRepository.colors }
    var activeTab by remember { mutableIntStateOf(0) } // 0: 16 Colors, 1: "Find the Color" Game

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightBg)
    ) {
        KidsTopBar(
            title = "Colors Learning 🌈",
            stars = stars,
            onBack = onBack,
            onSpeakTitle = { onSpeak("Colors Learning. Discover 16 beautiful rainbow colors!") }
        )

        // Tab Selector
        TabRow(
            selectedTabIndex = activeTab,
            containerColor = Color.White,
            contentColor = DeepSkyBlue,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp).clip(RoundedCornerShape(14.dp))
        ) {
            Tab(
                selected = activeTab == 0,
                onClick = { activeTab = 0 },
                text = { Text("16 Colors", fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = activeTab == 1,
                onClick = { activeTab = 1 },
                text = { Text("Find the Color 🎮", fontWeight = FontWeight.Bold) }
            )
        }

        if (activeTab == 0) {
            // Grid of 16 Color cards
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(14.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(colors) { item ->
                    Card(
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                        modifier = Modifier
                            .testTag("color_card_${item.name.lowercase()}")
                            .fillMaxWidth()
                            .border(1.5.dp, Color(0xFFEDF2F9), RoundedCornerShape(22.dp))
                            .bounceClick {
                                onSpeak("This is color ${item.name}. In Hindi: ${item.hindiName}. Examples: ${item.examples.joinToString(", ")}.")
                            }
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Large Color Swatch with 3D Floating Emoji
                            Box(
                                modifier = Modifier
                                    .size(76.dp)
                                    .clip(CircleShape)
                                    .background(item.color)
                                    .border(
                                        2.5.dp,
                                        if (item.name == "White" || item.name == "Beige") Color(0xFFCBD5E1) else Color.Transparent,
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = item.emojiExample,
                                    fontSize = 28.sp,
                                    modifier = Modifier.floating3D(verticalOffset = 3f, rotationDeg = 2.5f)
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = item.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = DarkNavy
                            )

                            Text(
                                text = item.hindiName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = ForestMint
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "e.g. ${item.examples.take(2).joinToString(", ")}",
                                fontSize = 11.sp,
                                color = SoftNavy,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            VoiceButton(
                                onSpeak = {
                                    onSpeak("This is color ${item.name}. In Hindi: ${item.hindiName}. Examples: ${item.examples.joinToString(", ")}.")
                                },
                                size = 36
                            )
                        }
                    }
                }
            }
        } else {
            // Find the Color Interactive Game
            FindTheColorGameView(onSpeak = onSpeak)
        }
    }
}

@Composable
fun FindTheColorGameView(onSpeak: (String) -> Unit) {
    val allColors = remember { KidsRepository.colors }
    var round by remember { mutableIntStateOf(0) }
    var selectedChoice by remember { mutableStateOf<ColorItem?>(null) }
    var showCelebration by remember { mutableStateOf(false) }

    // Randomize 4 colors with 1 target
    val currentTarget = remember(round) { allColors[round % allColors.size] }
    val currentChoices = remember(round) {
        val wrong = allColors.filter { it.name != currentTarget.name }.shuffled().take(3)
        (wrong + currentTarget).shuffled()
    }

    LaunchedEffect(round) {
        onSpeak("Can you find the color ${currentTarget.name}?")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Card(
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Find the Color Game 🌈", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = ForestMint)
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = "Can you find",
                    style = MaterialTheme.typography.titleLarge,
                    color = SoftNavy
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = currentTarget.name.uppercase(),
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Black,
                    color = currentTarget.color
                )
                Text(
                    text = "(${currentTarget.hindiName})",
                    fontSize = 16.sp,
                    color = DarkNavy,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // 4 Large Swatch Buttons
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(currentChoices) { choice ->
                val isSelected = selectedChoice?.name == choice.name
                val isCorrect = choice.name == currentTarget.name

                Box(
                    modifier = Modifier
                        .testTag("color_choice_${choice.name.lowercase()}")
                        .height(110.dp)
                        .clip(RoundedCornerShape(22.dp))
                        .background(choice.color)
                        .border(
                            if (isSelected) 4.dp else 1.5.dp,
                            if (isSelected && isCorrect) ForestMint else if (isSelected) Color.Red else Color(0x33000000),
                            RoundedCornerShape(22.dp)
                        )
                        .bounceClick {
                            selectedChoice = choice
                            if (isCorrect) {
                                onSpeak("Great job! That is ${choice.name}!")
                                showCelebration = true
                            } else {
                                onSpeak("Oops! That is ${choice.name}. Try again!")
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = choice.emojiExample,
                        fontSize = 36.sp,
                        modifier = Modifier.floating3D(verticalOffset = 2.5f, rotationDeg = 3f)
                    )
                }
            }
        }

        ConfettiCelebrationOverlay(isActive = showCelebration)

        if (showCelebration) {
            CelebrationDialog(
                title = "Color Star! 🌟",
                message = "You found ${currentTarget.name}! You earned 1 star! ⭐",
                starsEarned = 1,
                onDismiss = {
                    showCelebration = false
                    selectedChoice = null
                    round++
                }
            )
        }
    }
}
