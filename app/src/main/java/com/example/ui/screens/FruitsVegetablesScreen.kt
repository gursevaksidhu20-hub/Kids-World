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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.data.KidsRepository
import com.example.model.FruitVegItem
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
fun FruitsVegetablesScreen(
    stars: Int,
    onBack: () -> Unit,
    onSpeak: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = remember { KidsRepository.fruitsAndVeg }
    var selectedFilter by remember { mutableIntStateOf(0) } // 0: All (40), 1: Fruits (20), 2: Veggies (20)

    val displayedItems = remember(selectedFilter) {
        when (selectedFilter) {
            1 -> items.filter { it.isFruit }
            2 -> items.filter { !it.isFruit }
            else -> items
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightBg)
    ) {
        KidsTopBar(
            title = "Fruits & Veggies 🍎",
            stars = stars,
            onBack = onBack,
            onSpeakTitle = { onSpeak("Fruits and Vegetables. Learn about healthy, tasty foods!") }
        )

        // Filter Tabs
        TabRow(
            selectedTabIndex = selectedFilter,
            containerColor = Color.White,
            contentColor = DeepSkyBlue,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp).clip(RoundedCornerShape(14.dp))
        ) {
            Tab(
                selected = selectedFilter == 0,
                onClick = { selectedFilter = 0 },
                text = { Text("All 40 Items", fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = selectedFilter == 1,
                onClick = { selectedFilter = 1 },
                text = { Text("🍎 20 Fruits", fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = selectedFilter == 2,
                onClick = { selectedFilter = 2 },
                text = { Text("🥦 20 Veggies", fontWeight = FontWeight.Bold) }
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(displayedItems) { item ->
                Card(
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    modifier = Modifier
                        .testTag("fruit_veg_${item.name.lowercase()}")
                        .fillMaxWidth()
                        .border(1.5.dp, Color(0xFFEDF2F9), RoundedCornerShape(22.dp))
                        .bounceClick {
                            onSpeak("${item.name}. In Hindi: ${item.hindiName}. Fact: ${item.funFact}")
                        }
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
                                    .background(if (item.isFruit) Color(0xFFFFECEF) else Color(0xFFE8FDF0))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = if (item.isFruit) "Fruit" else "Vegetable",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (item.isFruit) Color(0xFFFF4D6D) else ForestMint
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
                        Text(
                            text = item.emoji,
                            fontSize = 52.sp,
                            modifier = Modifier.floating3D(verticalOffset = 3f, rotationDeg = 2f)
                        )
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
