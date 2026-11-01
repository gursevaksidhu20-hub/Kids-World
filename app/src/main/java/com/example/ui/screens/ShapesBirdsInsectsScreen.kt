package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.data.KidsRepository
import com.example.ui.components.KidsTopBar
import com.example.ui.components.VoiceButton
import com.example.ui.theme.DarkNavy
import com.example.ui.theme.DeepSkyBlue
import com.example.ui.theme.ForestMint
import com.example.ui.theme.LightBg
import com.example.ui.theme.SoftNavy

@Composable
fun ShapesBirdsInsectsScreen(
    stars: Int,
    onBack: () -> Unit,
    onSpeak: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val shapes = remember { KidsRepository.shapes }
    val allBirdsInsects = remember { KidsRepository.birdsAndInsects }
    val birds = remember { allBirdsInsects.filter { it.isBird } }
    val insects = remember { allBirdsInsects.filter { !it.isBird } }

    var selectedTab by remember { mutableIntStateOf(0) } // 0: Shapes (10), 1: Birds (10), 2: Insects (8)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightBg)
    ) {
        KidsTopBar(
            title = "Shapes & Birds ⭐",
            stars = stars,
            onBack = onBack,
            onSpeakTitle = { onSpeak("Shapes, Birds, and Insects. Tap any card to hear more!") }
        )

        // 3 Sub-tabs
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.White,
            contentColor = DeepSkyBlue,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp).clip(RoundedCornerShape(14.dp))
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("⭐ Shapes (10)", fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("🐦 Birds (10)", fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                text = { Text("🦋 Insects (8)", fontWeight = FontWeight.Bold) }
            )
        }

        when (selectedTab) {
            0 -> {
                // Shapes Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(shapes) { item ->
                        Card(
                            shape = RoundedCornerShape(22.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            modifier = Modifier
                                .testTag("shape_card_${item.name.lowercase()}")
                                .fillMaxWidth()
                                .border(1.5.dp, Color(0xFFEDF2F9), RoundedCornerShape(22.dp))
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color(0xFFFFF9DB))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = if (item.sides > 0) "${item.sides} Sides" else "Curved",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF8A6200)
                                        )
                                    }

                                    VoiceButton(
                                        onSpeak = {
                                            onSpeak("${item.name}. In Hindi: ${item.hindiName}. Fact: ${item.funFact}")
                                        },
                                        size = 32
                                    )
                                }

                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = item.emoji, fontSize = 52.sp)
                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = item.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = DarkNavy
                                )
                                Text(
                                    text = item.hindiName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = ForestMint
                                )

                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = item.funFact,
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

            1 -> {
                // Birds Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(birds) { item ->
                        Card(
                            shape = RoundedCornerShape(22.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            modifier = Modifier.fillMaxWidth().border(1.5.dp, Color(0xFFEDF2F9), RoundedCornerShape(22.dp))
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    VoiceButton(
                                        onSpeak = {
                                            onSpeak("${item.name}. In Hindi: ${item.hindiName}. Fact: ${item.funFact}")
                                        },
                                        size = 32
                                    )
                                }

                                Text(text = item.emoji, fontSize = 52.sp)
                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = item.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = DarkNavy
                                )
                                Text(
                                    text = item.hindiName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = ForestMint
                                )

                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = item.funFact,
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

            2 -> {
                // Insects Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(insects) { item ->
                        Card(
                            shape = RoundedCornerShape(22.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            modifier = Modifier.fillMaxWidth().border(1.5.dp, Color(0xFFEDF2F9), RoundedCornerShape(22.dp))
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    VoiceButton(
                                        onSpeak = {
                                            onSpeak("${item.name}. In Hindi: ${item.hindiName}. Fact: ${item.funFact}")
                                        },
                                        size = 32
                                    )
                                }

                                Text(text = item.emoji, fontSize = 52.sp)
                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = item.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = DarkNavy
                                )
                                Text(
                                    text = item.hindiName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = ForestMint
                                )

                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = item.funFact,
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
}
