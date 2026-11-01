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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.KidsRepository
import com.example.model.WordItem
import com.example.ui.components.KidsTopBar
import com.example.ui.components.VoiceButton
import com.example.ui.theme.DarkNavy
import com.example.ui.theme.DeepSkyBlue
import com.example.ui.theme.ForestMint
import com.example.ui.theme.LightBg
import com.example.ui.theme.SoftNavy

@Composable
fun VocabularyScreen(
    stars: Int,
    favoriteWords: Set<String>,
    onToggleFavorite: (String) -> Unit,
    onBack: () -> Unit,
    onSpeak: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val allWords = remember { KidsRepository.vocabulary }
    val categories = remember {
        listOf("All", "Body Parts", "Family", "School", "Clothes", "Food", "Nature", "Household", "Actions", "Feelings", "Opposites", "Favorites ❤️")
    }

    var selectedCategory by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }

    val filteredWords = remember(selectedCategory, searchQuery, favoriteWords) {
        val query = searchQuery.trim().lowercase()
        allWords.filter { item ->
            val matchCat = when (selectedCategory) {
                "All" -> true
                "Favorites ❤️" -> favoriteWords.contains(item.word)
                else -> item.category == selectedCategory
            }
            val matchQuery = query.isEmpty() ||
                    item.word.lowercase().contains(query) ||
                    item.hindiMeaning.lowercase().contains(query)
            matchCat && matchQuery
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightBg)
    ) {
        KidsTopBar(
            title = "English Words 📖",
            stars = stars,
            onBack = onBack,
            onSpeakTitle = { onSpeak("English Words Library. Explore over 150 vocabulary words with Hindi!") }
        )

        // Forgiving Search Field
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search word or meaning (e.g. Cat, Book, Smile)...", fontSize = 13.sp) },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = SoftNavy)
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White
            ),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 6.dp)
                .testTag("vocab_search_field")
        )

        // Category Filter Ribbon
        LazyRow(
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { cat ->
                val isSelected = cat == selectedCategory
                Box(
                    modifier = Modifier
                        .testTag("vocab_cat_$cat")
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

        // Vocabulary List
        Text(
            text = "Showing ${filteredWords.size} words:",
            fontSize = 12.sp,
            color = SoftNavy,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )

        LazyColumn(
            contentPadding = PaddingValues(start = 14.dp, end = 14.dp, top = 4.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredWords) { item ->
                val isFav = favoriteWords.contains(item.word)
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .testTag("vocab_item_${item.word.lowercase()}")
                        .fillMaxWidth()
                        .border(1.5.dp, Color(0xFFEDF2F9), RoundedCornerShape(20.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Left: Emoji avatar
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0xFFEBF5FF)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = item.emoji, fontSize = 30.sp)
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        // Middle: Word, Hindi, Example phrase
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = item.word,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = DarkNavy
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFFF1F5F9))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(text = item.category, fontSize = 10.sp, color = SoftNavy)
                                }
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = item.hindiMeaning,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = ForestMint
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "\"${item.examplePhrase}\"",
                                fontSize = 11.sp,
                                color = SoftNavy,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                            )
                        }

                        // Right: Actions (Voice & Favorite)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            VoiceButton(
                                onSpeak = {
                                    onSpeak("${item.word}. ${item.hindiMeaning}. ${item.examplePhrase}")
                                },
                                size = 36
                            )

                            IconButton(
                                onClick = { onToggleFavorite(item.word) },
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
                    }
                }
            }
        }
    }
}
