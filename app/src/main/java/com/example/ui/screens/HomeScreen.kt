package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppSection
import com.example.model.UserProgress
import com.example.ui.components.StarsBadge
import com.example.ui.components.VoiceButton
import com.example.ui.components.bounceClick
import com.example.ui.components.floating3D
import com.example.ui.theme.DarkNavy
import com.example.ui.theme.DeepSkyBlue
import com.example.ui.theme.ForestMint
import com.example.ui.theme.LightBg
import com.example.ui.theme.MintGreen
import com.example.ui.theme.SkyBlue
import com.example.ui.theme.SoftNavy
import com.example.ui.theme.SoftPink
import com.example.ui.theme.SunnyYellow
import com.example.ui.theme.WarmGold

data class HomeCategoryItem(
    val section: AppSection,
    val subtitle: String,
    val bgGradient: List<Color>,
    val badgeTag: String? = null
)

@Composable
fun HomeScreen(
    progress: UserProgress,
    onNavigate: (AppSection) -> Unit,
    onSpeak: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = remember {
        listOf(
            HomeCategoryItem(
                AppSection.ABC,
                "A to Z Phonics & Tracing",
                listOf(Color(0xFFFF9A9E), Color(0xFFFAD0C4)),
                "26 Letters"
            ),
            HomeCategoryItem(
                AppSection.NUMBERS,
                "Counting & Number Names",
                listOf(Color(0xFFFFE066), Color(0xFFFFD166)),
                "1–100"
            ),
            HomeCategoryItem(
                AppSection.COLORING,
                "Interactive Region Fill",
                listOf(Color(0xFF70C8FF), Color(0xFF4D96FF)),
                "30 Pages"
            ),
            HomeCategoryItem(
                AppSection.DRAWING,
                "Finger Canvas & Stamps",
                listOf(Color(0xFF8FE3B0), Color(0xFF6BCB77)),
                "Studio"
            ),
            HomeCategoryItem(
                AppSection.ANIMALS,
                "Farm, Wild & Ocean Friends",
                listOf(Color(0xFFFFB347), Color(0xFFFFCC33)),
                "40 Animals"
            ),
            HomeCategoryItem(
                AppSection.COLORS,
                "16 Colors & 'Find Color'",
                listOf(Color(0xFFC8B6FF), Color(0xFF9D4EDD)),
                "Quiz"
            ),
            HomeCategoryItem(
                AppSection.WORDS,
                "150+ Words with Hindi",
                listOf(Color(0xFF84DCC6), Color(0xFF48CAE4)),
                "Library"
            ),
            HomeCategoryItem(
                AppSection.FRUITS_VEGGIES,
                "Healthy Food & Fun Facts",
                listOf(Color(0xFFFF8FA3), Color(0xFFFF6584)),
                "40 Items"
            ),
            HomeCategoryItem(
                AppSection.SHAPES_BIRDS,
                "Shapes, Birds & Insects",
                listOf(Color(0xFFFDCB6E), Color(0xFFE17055)),
                "Explore"
            ),
            HomeCategoryItem(
                AppSection.WRITING,
                "Trace Letters & Shapes",
                listOf(Color(0xFF74B9FF), Color(0xFF0984E3)),
                "Practice"
            ),
            HomeCategoryItem(
                AppSection.GAMES,
                "8 Fun Educational Quizzes",
                listOf(Color(0xFFFAB1A0), Color(0xFFFF7675)),
                "Mini Games"
            ),
            HomeCategoryItem(
                AppSection.RHYMES,
                "10 Classic Nursery Rhymes",
                listOf(Color(0xFFA29BFE), Color(0xFF6C5CE7)),
                "Sing Along"
            ),
            HomeCategoryItem(
                AppSection.REWARDS,
                "Badges & Star Milestones",
                listOf(Color(0xFFFFEAA7), Color(0xFFFDCB6E)),
                "Badges"
            ),
            HomeCategoryItem(
                AppSection.PROGRESS,
                "Learning Stats & Parent Hub",
                listOf(Color(0xFF55EFC4), Color(0xFF00B894)),
                "Parent Hub"
            )
        )
    }

    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isVisible = true
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(start = 14.dp, end = 14.dp, top = 8.dp, bottom = 28.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = modifier
            .fillMaxSize()
            .background(LightBg)
    ) {
        // Welcome Mascot Hero Banner
        item(span = { GridItemSpan(2) }) {
            MascotHeroHeader(
                stars = progress.totalStars,
                onSpeak = {
                    onSpeak("Welcome to Kids World! Let's learn, color, and play together!")
                }
            )
        }

        // Quick Stats Pill Row
        item(span = { GridItemSpan(2) }) {
            QuickStatsRow(progress = progress)
        }

        // 14 Interactive Category Cards
        items(categories) { item ->
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn() + slideInVertically(
                    initialOffsetY = { 40 },
                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                )
            ) {
                CategoryGridCard(
                    item = item,
                    onClick = {
                        onSpeak(item.section.title)
                        onNavigate(item.section)
                    }
                )
            }
        }
    }
}

@Composable
private fun MascotHeroHeader(
    stars: Int,
    onSpeak: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp)
            .shadow(4.dp, RoundedCornerShape(26.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        listOf(Color(0xFF70C8FF).copy(alpha = 0.2f), Color(0xFFFFD85E).copy(alpha = 0.25f))
                    )
                )
                .padding(18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Mascot & Speech
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    // 3D Floating Mascot Avatar
                    Box(
                        modifier = Modifier
                            .floating3D(verticalOffset = 6f, rotationDeg = 4f)
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    listOf(SunnyYellow, WarmGold)
                                )
                            )
                            .border(3.dp, Color.White, CircleShape)
                            .shadow(8.dp, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🦁", fontSize = 42.sp)
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Kids World",
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    fontWeight = FontWeight.Black,
                                    color = DarkNavy
                                )
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            VoiceButton(onSpeak = onSpeak, size = 32)
                        }
                        Text(
                            text = "Learn • Color • Play • Grow ✨",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = SoftNavy,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }

                // Stars Display
                StarsBadge(stars = stars)
            }
        }
    }
}

@Composable
private fun QuickStatsRow(progress: UserProgress) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        StatItem(label = "ABC Explored", value = "${progress.lettersExplored.size}/26", icon = "🔤")
        Box(modifier = Modifier.width(1.dp).height(24.dp).background(Color(0xFFE2E8F0)))
        StatItem(label = "1–100 Count", value = "${progress.numbersExplored.size}/100", icon = "🔢")
        Box(modifier = Modifier.width(1.dp).height(24.dp).background(Color(0xFFE2E8F0)))
        StatItem(label = "Art Saved", value = "${progress.completedColoringPages.size}", icon = "🎨")
        Box(modifier = Modifier.width(1.dp).height(24.dp).background(Color(0xFFE2E8F0)))
        StatItem(label = "Quizzes", value = "${progress.quizCorrectAnswers}", icon = "🏆")
    }
}

@Composable
private fun StatItem(label: String, value: String, icon: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "$icon $value", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = DarkNavy)
        Text(text = label, fontSize = 11.sp, color = SoftNavy)
    }
}

@Composable
private fun CategoryGridCard(
    item: HomeCategoryItem,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .testTag("home_card_${item.section.name.lowercase()}")
            .fillMaxWidth()
            .height(156.dp)
            .bounceClick(onClick = onClick)
            .border(
                width = 2.dp,
                brush = Brush.verticalGradient(
                    listOf(Color.White, Color(0xFFEDF2F9), Color(0xFFDCE6F2))
                ),
                shape = RoundedCornerShape(24.dp)
            )
            .shadow(6.dp, RoundedCornerShape(24.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Icon & Badge Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Colored Circle for Emoji Icon
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Brush.linearGradient(item.bgGradient)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = item.section.icon, fontSize = 28.sp)
                }

                if (item.badgeTag != null) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFF1F5F9))
                            .padding(horizontal = 7.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = item.badgeTag,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SoftNavy
                        )
                    }
                }
            }

            // Title & Subtitle
            Column {
                Text(
                    text = item.section.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = DarkNavy
                    ),
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = item.subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = SoftNavy,
                        fontSize = 11.sp
                    ),
                    maxLines = 1
                )
            }
        }
    }
}
