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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.KidsRepository
import com.example.model.Badge
import com.example.model.UserProgress
import com.example.ui.components.KidsTopBar
import com.example.ui.components.StarsBadge
import com.example.ui.components.bounceClick
import com.example.ui.components.floating3D
import com.example.ui.theme.DarkNavy
import com.example.ui.theme.DeepSkyBlue
import com.example.ui.theme.ForestMint
import com.example.ui.theme.LightBg
import com.example.ui.theme.SoftNavy
import com.example.ui.theme.SunnyYellow
import com.example.ui.theme.WarmGold

@Composable
fun RewardsScreen(
    progress: UserProgress,
    onBack: () -> Unit,
    onSpeak: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val badges = remember { KidsRepository.badges }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightBg)
    ) {
        KidsTopBar(
            title = "My Rewards 🏆",
            stars = progress.totalStars,
            onBack = onBack,
            onSpeakTitle = { onSpeak("My Rewards. You have earned ${progress.totalStars} stars!") }
        )

        // Star Trophy Banner
        Card(
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
                .shadow(4.dp, RoundedCornerShape(26.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFFFFF9DB), SunnyYellow.copy(alpha = 0.35f))
                        )
                    )
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Star Champion! 🌟",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Black,
                            color = DarkNavy
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Keep playing and learning to collect all badges!",
                            fontSize = 12.sp,
                            color = SoftNavy
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(68.dp)
                            .clip(CircleShape)
                            .background(WarmGold),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "👑",
                            fontSize = 36.sp,
                            modifier = Modifier.floating3D(verticalOffset = 4f, rotationDeg = 3f)
                        )
                    }
                }
            }
        }

        Text(
            text = "Badges Collection (${progress.unlockedBadges.size} of ${badges.size} Unlocked):",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = DarkNavy,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )

        // Badges Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(badges) { badge ->
                val isUnlocked = progress.unlockedBadges.contains(badge.id)

                Card(
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isUnlocked) Color.White else Color(0xFFF8FAFC)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = if (isUnlocked) 3.dp else 0.dp),
                    modifier = Modifier
                        .testTag("badge_${badge.id}")
                        .fillMaxWidth()
                        .border(
                            1.5.dp,
                            if (isUnlocked) WarmGold else Color(0xFFE2E8F0),
                            RoundedCornerShape(22.dp)
                        )
                        .bounceClick {
                            if (isUnlocked) {
                                onSpeak("Great job! You earned the ${badge.name} badge! ${badge.description}")
                            } else {
                                onSpeak("This badge is locked. Earn ${badge.requiredStars} stars to unlock!")
                            }
                        }
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(if (isUnlocked) Color(0xFFFFF9DB) else Color(0xFFE2E8F0)),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isUnlocked) {
                                Text(
                                    text = badge.emoji,
                                    fontSize = 34.sp,
                                    modifier = Modifier.floating3D(verticalOffset = 3f, rotationDeg = 3f)
                                )
                            } else {
                                Icon(Icons.Default.Lock, contentDescription = "Locked", tint = Color.Gray, modifier = Modifier.size(24.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = badge.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = if (isUnlocked) DarkNavy else Color.Gray,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = badge.description,
                            fontSize = 11.sp,
                            color = if (isUnlocked) SoftNavy else Color.Gray,
                            textAlign = TextAlign.Center,
                            maxLines = 2
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        if (isUnlocked) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(ForestMint.copy(alpha = 0.15f))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text("UNLOCKED ✨", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = ForestMint)
                            }
                        } else {
                            Text(
                                text = "Req: ${badge.requiredStars} ⭐",
                                fontSize = 10.sp,
                                color = Color.Gray,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
