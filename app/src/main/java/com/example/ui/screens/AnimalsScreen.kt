package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.KidsQuizData
import com.example.data.KidsRepository
import com.example.model.AnimalItem
import com.example.ui.components.CelebrationDialog
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
fun AnimalsScreen(
    stars: Int,
    favoriteWords: Set<String>,
    onToggleFavorite: (String) -> Unit,
    onBack: () -> Unit,
    onSpeak: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val animals = remember { KidsRepository.animals }
    val categories = remember { listOf("All", "Wild", "Farm", "Pets", "Sea", "Favorites ❤️") }
    var selectedCategory by remember { mutableStateOf("All") }
    var activeTab by remember { mutableIntStateOf(0) } // 0: Encyclopedia, 1: Animal Quiz

    val filteredAnimals = remember(selectedCategory, favoriteWords) {
        when (selectedCategory) {
            "All" -> animals
            "Favorites ❤️" -> animals.filter { favoriteWords.contains(it.name) }
            else -> animals.filter { it.category == selectedCategory }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightBg)
    ) {
        KidsTopBar(
            title = "Animals Encyclopedia 🦁",
            stars = stars,
            onBack = onBack,
            onSpeakTitle = { onSpeak("Animal Encyclopedia. Explore 40 animal friends!") }
        )

        // Mode Tab Bar
        TabRow(
            selectedTabIndex = activeTab,
            containerColor = Color.White,
            contentColor = DeepSkyBlue,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp).clip(RoundedCornerShape(14.dp))
        ) {
            Tab(
                selected = activeTab == 0,
                onClick = { activeTab = 0 },
                text = { Text("Explore Animals", fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = activeTab == 1,
                onClick = { activeTab = 1 },
                text = { Text("Animal Quiz 🐾", fontWeight = FontWeight.Bold) }
            )
        }

        if (activeTab == 0) {
            // Category Chips
            LazyRow(
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { cat ->
                    val isSelected = cat == selectedCategory
                    Box(
                        modifier = Modifier
                            .testTag("animal_cat_$cat")
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isSelected) DeepSkyBlue else Color.White)
                            .border(1.5.dp, if (isSelected) DeepSkyBlue else Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
                            .clickable { selectedCategory = cat }
                            .padding(horizontal = 14.dp, vertical = 7.dp)
                    ) {
                        Text(
                            text = cat,
                            color = if (isSelected) Color.White else DarkNavy,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // Grid of Animals
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(start = 14.dp, end = 14.dp, top = 6.dp, bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredAnimals) { animal ->
                    val isFav = favoriteWords.contains(animal.name)
                    Card(
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                        modifier = Modifier
                            .testTag("animal_card_${animal.id}")
                            .fillMaxWidth()
                            .border(1.5.dp, Color(0xFFEDF2F9), RoundedCornerShape(22.dp))
                            .bounceClick {
                                onSpeak("${animal.name}. In Hindi: ${animal.hindiName}. Sound: ${animal.sound}. Fact: ${animal.funFact}")
                            }
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Top action icons
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                VoiceButton(
                                    onSpeak = {
                                        onSpeak("${animal.name}. In Hindi: ${animal.hindiName}. Sound: ${animal.sound}. Fact: ${animal.funFact}")
                                    },
                                    size = 36
                                )

                                IconButton(
                                    onClick = { onToggleFavorite(animal.name) },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                        contentDescription = "Favorite",
                                        tint = if (isFav) Color.Red else SoftNavy,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }

                            // Animal Emoji Illustration with 3D float
                            Text(
                                text = animal.emoji,
                                fontSize = 54.sp,
                                modifier = Modifier.floating3D(verticalOffset = 3.5f, rotationDeg = 2.5f)
                            )
                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = animal.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = DarkNavy
                            )
                            Text(
                                text = animal.hindiName,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = ForestMint
                            )

                            Spacer(modifier = Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFFFFF9DB))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "🔊 ${animal.sound}",
                                    fontSize = 11.sp,
                                    color = Color(0xFF8A6200),
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = animal.funFact,
                                fontSize = 11.sp,
                                color = SoftNavy,
                                textAlign = TextAlign.Center,
                                maxLines = 2
                            )
                        }
                    }
                }
            }
        } else {
            // Animal Quiz Mode
            AnimalQuizView(onSpeak = onSpeak)
        }
    }
}

@Composable
fun AnimalQuizView(onSpeak: (String) -> Unit) {
    val questions = remember { KidsQuizData.animalQuestions }
    var qIndex by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableIntStateOf(-1) }
    var showDialog by remember { mutableStateOf(false) }

    val current = questions[qIndex % questions.size]

    LaunchedEffect(qIndex) {
        onSpeak(current.prompt)
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
                Text(text = "Animal Quiz 🐾", fontWeight = FontWeight.Bold, color = ForestMint, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(14.dp))
                Text(text = current.visualHint, fontSize = 72.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = current.prompt,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = DarkNavy,
                    textAlign = TextAlign.Center
                )
            }
        }

        // 4 Options
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            current.options.forEachIndexed { idx, opt ->
                val isRight = idx == current.correctIndex
                val isSel = selectedOption == idx
                val bg = when {
                    isSel && isRight -> Color(0xFFD4EDDA)
                    isSel && !isRight -> Color(0xFFF8D7DA)
                    else -> Color.White
                }
                val border = when {
                    isSel && isRight -> ForestMint
                    isSel && !isRight -> Color.Red
                    else -> Color(0xFFE2E8F0)
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(bg)
                        .border(1.5.dp, border, RoundedCornerShape(16.dp))
                        .clickable {
                            selectedOption = idx
                            if (isRight) {
                                onSpeak(current.encouragement)
                                showDialog = true
                            } else {
                                onSpeak("Try again! You can do it!")
                            }
                        }
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(text = opt, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DarkNavy)
                }
            }
        }

        if (showDialog) {
            CelebrationDialog(
                title = "Animal Expert! 🦁",
                message = "${current.encouragement} You earned 2 stars! ⭐",
                starsEarned = 2,
                onDismiss = {
                    showDialog = false
                    selectedOption = -1
                    qIndex++
                }
            )
        }
    }
}
