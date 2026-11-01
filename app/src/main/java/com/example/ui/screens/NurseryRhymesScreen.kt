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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.model.RhymeItem
import com.example.ui.components.KidsTopBar
import com.example.ui.components.floating3D
import com.example.ui.theme.DarkNavy
import com.example.ui.theme.DeepSkyBlue
import com.example.ui.theme.ForestMint
import com.example.ui.theme.LightBg
import com.example.ui.theme.SoftNavy
import com.example.ui.theme.SunnyYellow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NurseryRhymesScreen(
    stars: Int,
    onBack: () -> Unit,
    onSpeak: (String) -> Unit,
    onStopSpeech: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rhymes = remember { KidsRepository.nurseryRhymes }
    var selectedRhymeIndex by remember { mutableIntStateOf(0) }
    var isPlaying by remember { mutableStateOf(false) }
    var currentHighlightLine by remember { mutableIntStateOf(-1) }

    val currentRhyme = rhymes[selectedRhymeIndex]
    val scope = rememberCoroutineScope()

    // Stop audio when changing rhyme
    LaunchedEffect(selectedRhymeIndex) {
        isPlaying = false
        currentHighlightLine = -1
        onStopSpeech()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightBg)
    ) {
        KidsTopBar(
            title = "Nursery Rhymes 🎵",
            stars = stars,
            onBack = {
                onStopSpeech()
                onBack()
            },
            onSpeakTitle = { onSpeak("Nursery Rhymes. Sing along with 10 classic songs!") }
        )

        // Rhymes Horizontal Selector
        LazyRow(
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(rhymes) { idx, rhyme ->
                val isSel = idx == selectedRhymeIndex
                Box(
                    modifier = Modifier
                        .testTag("rhyme_chip_${idx}")
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isSel) DeepSkyBlue else Color.White)
                        .border(1.5.dp, if (isSel) DeepSkyBlue else Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
                        .clickable { selectedRhymeIndex = idx }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "${rhyme.emoji} ${rhyme.title}",
                        color = if (isSel) Color.White else DarkNavy,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Rhyme Reader Card with Karaoake-style Line Highlighting
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header: Emoji & Title
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = currentRhyme.emoji,
                        fontSize = 42.sp,
                        modifier = Modifier.floating3D(verticalOffset = 3.5f, rotationDeg = 3f)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = currentRhyme.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = DarkNavy
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Rhyme Lines with active karaoke highlight
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    itemsIndexed(currentRhyme.lines) { lineIdx, lineText ->
                        val isHighlighted = lineIdx == currentHighlightLine
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(if (isHighlighted) Color(0xFFFFF9DB) else Color.Transparent)
                                .border(
                                    1.dp,
                                    if (isHighlighted) Color(0xFFFFAA00) else Color.Transparent,
                                    RoundedCornerShape(14.dp)
                                )
                                .padding(vertical = 6.dp, horizontal = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = lineText,
                                fontSize = if (isHighlighted) 17.sp else 15.sp,
                                fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.Medium,
                                color = if (isHighlighted) Color(0xFF805B00) else DarkNavy,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Playback Controls Strip
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Play / Pause Button
                    Button(
                        onClick = {
                            if (!isPlaying) {
                                isPlaying = true
                                scope.launch {
                                    for (i in currentRhyme.lines.indices) {
                                        if (!isPlaying) break
                                        currentHighlightLine = i
                                        onSpeak(currentRhyme.lines[i])
                                        delay(3200) // Pace between lines
                                    }
                                    isPlaying = false
                                    currentHighlightLine = -1
                                }
                            } else {
                                isPlaying = false
                                currentHighlightLine = -1
                                onStopSpeech()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isPlaying) Color(0xFFFF6B6B) else ForestMint
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.testTag("rhyme_play_button")
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isPlaying) "Pause" else "Play",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isPlaying) "Pause Reading" else "Read Rhyme Aloud 🎤",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    IconButton(
                        onClick = {
                            isPlaying = false
                            currentHighlightLine = -1
                            onStopSpeech()
                            // Restart immediately
                            scope.launch {
                                delay(200)
                                isPlaying = true
                                for (i in currentRhyme.lines.indices) {
                                    if (!isPlaying) break
                                    currentHighlightLine = i
                                    onSpeak(currentRhyme.lines[i])
                                    delay(3200)
                                }
                                isPlaying = false
                                currentHighlightLine = -1
                            }
                        },
                        modifier = Modifier
                            .size(46.dp)
                            .background(Color(0xFFF1F5F9), CircleShape)
                            .testTag("rhyme_replay_button")
                    ) {
                        Icon(Icons.Default.Replay, contentDescription = "Replay", tint = DarkNavy)
                    }
                }
            }
        }
    }
}
