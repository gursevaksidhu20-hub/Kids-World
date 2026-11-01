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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.UserProgress
import com.example.ui.components.KidsTopBar
import com.example.ui.components.ParentGateDialog
import com.example.ui.theme.DarkNavy
import com.example.ui.theme.DeepSkyBlue
import com.example.ui.theme.ForestMint
import com.example.ui.theme.LightBg
import com.example.ui.theme.SoftNavy

@Composable
fun ProgressScreen(
    progress: UserProgress,
    onResetAllData: () -> Unit,
    onBack: () -> Unit,
    onSpeak: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showParentGate by remember { mutableStateOf(false) }
    var showResetConfirmation by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightBg)
    ) {
        KidsTopBar(
            title = "Learning Progress 📊",
            stars = progress.totalStars,
            onBack = onBack,
            onSpeakTitle = { onSpeak("Learning Progress dashboard. View all your wonderful learning achievements!") }
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            // Overall Stars Overview Card
            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth().shadow(3.dp, RoundedCornerShape(24.dp))
                ) {
                    Row(
                        modifier = Modifier.padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFFFF9DB)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "⭐", fontSize = 32.sp)
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Text(
                                text = "${progress.totalStars} Golden Stars Earned",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = DarkNavy
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Every activity and quiz completed adds stars!",
                                fontSize = 12.sp,
                                color = SoftNavy
                            )
                        }
                    }
                }
            }

            // Learning Activity Metrics
            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "Learning Metrics 📈",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = DarkNavy
                        )

                        // 1. Alphabet Progress
                        ProgressBarItem(
                            label = "Alphabets Explored",
                            current = progress.lettersExplored.size,
                            total = 26,
                            icon = "🔤",
                            color = DeepSkyBlue
                        )

                        // 2. Numbers Progress
                        ProgressBarItem(
                            label = "Numbers 1–100 Practiced",
                            current = progress.numbersExplored.size,
                            total = 100,
                            icon = "🔢",
                            color = Color(0xFFFFB703)
                        )

                        // 3. Coloring Pages
                        ProgressBarItem(
                            label = "Coloring Pages Completed",
                            current = progress.completedColoringPages.size,
                            total = 30,
                            icon = "🎨",
                            color = Color(0xFFFF6584)
                        )

                        // 4. Vocabulary Words
                        ProgressBarItem(
                            label = "Vocabulary Words Discovered",
                            current = progress.wordsExplored.size,
                            total = 160,
                            icon = "📖",
                            color = ForestMint
                        )

                        // 5. Quiz Performance
                        ProgressBarItem(
                            label = "Quizzes Solved Correctly",
                            current = progress.quizCorrectAnswers,
                            total = (progress.quizzesTaken).coerceAtLeast(10),
                            icon = "🏆",
                            color = Color(0xFF7A5AF8)
                        )
                    }
                }
            }

            // Privacy & Child Safety Card
            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
                    modifier = Modifier.fillMaxWidth().border(1.5.dp, ForestMint.copy(alpha = 0.4f), RoundedCornerShape(24.dp))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Security, contentDescription = null, tint = ForestMint, modifier = Modifier.size(32.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("100% Child-Safe & Private 🛡️", fontWeight = FontWeight.Bold, color = DarkNavy, fontSize = 14.sp)
                            Text(
                                "All progress and drawings remain private on this device. No personal data or accounts required.",
                                fontSize = 11.sp,
                                color = SoftNavy
                            )
                        }
                    }
                }
            }

            // Parent Gate Data Reset Button
            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Parent Zone 🔒", fontWeight = FontWeight.Bold, color = DarkNavy, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Resetting all progress will clear stars and saved art. This action requires adult confirmation.",
                            fontSize = 11.sp,
                            color = SoftNavy,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedButton(
                            onClick = { showParentGate = true },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.testTag("parent_reset_button")
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Reset Learning Data", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    // Parent Gate verification dialog
    if (showParentGate) {
        ParentGateDialog(
            onSuccess = {
                showParentGate = false
                showResetConfirmation = true
            },
            onDismiss = { showParentGate = false }
        )
    }

    // Reset confirmation
    if (showResetConfirmation) {
        AlertDialog(
            onDismissRequest = { showResetConfirmation = false },
            title = { Text("Confirm Data Reset?", fontWeight = FontWeight.Bold) },
            text = { Text("Are you sure you want to reset all stars, drawings, and progress to factory defaults?") },
            confirmButton = {
                Button(
                    onClick = {
                        onResetAllData()
                        showResetConfirmation = false
                        onSpeak("All data has been reset.")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Yes, Reset Everything", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetConfirmation = false }) {
                    Text("Cancel")
                }
            },
            shape = RoundedCornerShape(20.dp)
        )
    }
}

@Composable
private fun ProgressBarItem(
    label: String,
    current: Int,
    total: Int,
    icon: String,
    color: Color
) {
    val progressFraction = (current.toFloat() / total.toFloat()).coerceIn(0f, 1f)

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "$icon $label", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = DarkNavy)
            Text(text = "$current / $total", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SoftNavy)
        }
        Spacer(modifier = Modifier.height(6.dp))
        LinearProgressIndicator(
            progress = { progressFraction },
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(8.dp)),
            color = color,
            trackColor = color.copy(alpha = 0.2f)
        )
    }
}
