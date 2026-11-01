package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.UserProgressManager
import com.example.model.AppSection
import com.example.ui.screens.AlphabetScreen
import com.example.ui.screens.AnimalsScreen
import com.example.ui.screens.ColoringBookScreen
import com.example.ui.screens.ColorsScreen
import com.example.ui.screens.EducationalGamesScreen
import com.example.ui.screens.FreeDrawingScreen
import com.example.ui.screens.FruitsVegetablesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.NumbersScreen
import com.example.ui.screens.NurseryRhymesScreen
import com.example.ui.screens.ProgressScreen
import com.example.ui.screens.RewardsScreen
import com.example.ui.screens.ShapesBirdsInsectsScreen
import com.example.ui.screens.VocabularyScreen
import com.example.ui.screens.WritingPracticeScreen
import com.example.ui.theme.DarkNavy
import com.example.ui.theme.DeepSkyBlue
import com.example.ui.theme.KidsWorldTheme
import com.example.ui.theme.LightBg
import com.example.ui.theme.SoftNavy
import com.example.util.SpeechHelper

class MainActivity : ComponentActivity() {

    private lateinit var speechHelper: SpeechHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        speechHelper = SpeechHelper(this)

        setContent {
            val progressManager = remember { UserProgressManager(this) }

            KidsWorldTheme {
                KidsWorldApp(
                    speechHelper = speechHelper,
                    progressManager = progressManager
                )
            }
        }
    }

    override fun onDestroy() {
        speechHelper.shutdown()
        super.onDestroy()
    }
}

@Composable
fun KidsWorldApp(
    speechHelper: SpeechHelper,
    progressManager: UserProgressManager
) {
    val navStack = remember { mutableStateListOf(AppSection.HOME) }
    val currentSection = navStack.lastOrNull() ?: AppSection.HOME
    var tracingTarget by remember { mutableStateOf("A") }

    val progress by progressManager.progressState
    val savedArtworks by progressManager.savedArtworks

    fun navigateTo(section: AppSection) {
        if (currentSection != section) {
            navStack.add(section)
        }
    }

    fun navigateBack() {
        if (navStack.size > 1) {
            navStack.removeAt(navStack.lastIndex)
        }
    }

    fun navigateHome() {
        navStack.clear()
        navStack.add(AppSection.HOME)
    }

    // Android Hardware / Gesture BackHandler
    BackHandler(enabled = navStack.size > 1) {
        speechHelper.stop()
        navigateBack()
    }

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = {
            // Persistent Friendly Bottom Navigation Bar
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 6.dp,
                modifier = Modifier
                    .navigationBarsPadding()
                    .testTag("kids_bottom_nav")
            ) {
                val navItems = listOf(
                    Triple(AppSection.HOME, "Home", Icons.Default.Home),
                    Triple(AppSection.COLORING, "Coloring", Icons.Default.Palette),
                    Triple(AppSection.DRAWING, "Draw", Icons.Default.Create),
                    Triple(AppSection.GAMES, "Games", Icons.Default.Games),
                    Triple(AppSection.REWARDS, "Rewards", Icons.Default.Star)
                )

                navItems.forEach { (section, label, icon) ->
                    val isSelected = currentSection == section
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            speechHelper.stop()
                            if (section == AppSection.HOME) {
                                navigateHome()
                            } else {
                                navigateTo(section)
                            }
                            speechHelper.speak(label)
                        },
                        icon = {
                            Icon(
                                imageVector = icon,
                                contentDescription = label,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = {
                            Text(
                                text = label,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = DeepSkyBlue,
                            selectedTextColor = DeepSkyBlue,
                            unselectedIconColor = SoftNavy,
                            unselectedTextColor = SoftNavy,
                            indicatorColor = Color(0xFFE8F4FD)
                        ),
                        modifier = Modifier.testTag("nav_item_${section.name.lowercase()}")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(LightBg)
        ) {
            AnimatedContent(
                targetState = currentSection,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "section_navigation_anim"
            ) { section ->
                when (section) {
                    AppSection.HOME -> {
                        HomeScreen(
                            progress = progress,
                            onNavigate = { sec ->
                                navigateTo(sec)
                            },
                            onSpeak = { text -> speechHelper.speak(text) }
                        )
                    }

                    AppSection.ABC -> {
                        AlphabetScreen(
                            stars = progress.totalStars,
                            onBack = { navigateBack() },
                            onSpeak = { text -> speechHelper.speak(text) },
                            onRecordLetter = { letter -> progressManager.recordLetterExplored(letter) },
                            onGoToTracing = { letter ->
                                tracingTarget = letter.toString()
                                navigateTo(AppSection.WRITING)
                            }
                        )
                    }

                    AppSection.NUMBERS -> {
                        NumbersScreen(
                            stars = progress.totalStars,
                            onBack = { navigateBack() },
                            onSpeak = { text -> speechHelper.speak(text) },
                            onRecordNumber = { num -> progressManager.recordNumberExplored(num) },
                            onGoToTracing = { num ->
                                tracingTarget = num.toString()
                                navigateTo(AppSection.WRITING)
                            }
                        )
                    }

                    AppSection.COLORING -> {
                        ColoringBookScreen(
                            stars = progress.totalStars,
                            onBack = { navigateBack() },
                            onSaveArtwork = { artwork ->
                                progressManager.saveArtwork(artwork)
                                if (artwork.pageId != null) {
                                    progressManager.recordColoringPageCompleted(artwork.pageId)
                                }
                            },
                            savedArtworks = savedArtworks,
                            onSpeak = { text -> speechHelper.speak(text) }
                        )
                    }

                    AppSection.DRAWING -> {
                        FreeDrawingScreen(
                            stars = progress.totalStars,
                            onBack = { navigateBack() },
                            onSaveArtwork = { artwork -> progressManager.saveArtwork(artwork) },
                            onSpeak = { text -> speechHelper.speak(text) }
                        )
                    }

                    AppSection.ANIMALS -> {
                        AnimalsScreen(
                            stars = progress.totalStars,
                            favoriteWords = progress.favoriteWords,
                            onToggleFavorite = { word -> progressManager.toggleFavoriteWord(word) },
                            onBack = { navigateBack() },
                            onSpeak = { text -> speechHelper.speak(text) }
                        )
                    }

                    AppSection.COLORS -> {
                        ColorsScreen(
                            stars = progress.totalStars,
                            onBack = { navigateBack() },
                            onSpeak = { text -> speechHelper.speak(text) }
                        )
                    }

                    AppSection.WORDS -> {
                        VocabularyScreen(
                            stars = progress.totalStars,
                            favoriteWords = progress.favoriteWords,
                            onToggleFavorite = { word -> progressManager.toggleFavoriteWord(word) },
                            onBack = { navigateBack() },
                            onSpeak = { text -> speechHelper.speak(text) }
                        )
                    }

                    AppSection.FRUITS_VEGGIES -> {
                        FruitsVegetablesScreen(
                            stars = progress.totalStars,
                            onBack = { navigateBack() },
                            onSpeak = { text -> speechHelper.speak(text) }
                        )
                    }

                    AppSection.SHAPES_BIRDS -> {
                        ShapesBirdsInsectsScreen(
                            stars = progress.totalStars,
                            onBack = { navigateBack() },
                            onSpeak = { text -> speechHelper.speak(text) }
                        )
                    }

                    AppSection.WRITING -> {
                        WritingPracticeScreen(
                            stars = progress.totalStars,
                            initialTarget = tracingTarget,
                            onBack = { navigateBack() },
                            onSpeak = { text -> speechHelper.speak(text) },
                            onRecordPractice = { progressManager.addStars(2) }
                        )
                    }

                    AppSection.GAMES -> {
                        EducationalGamesScreen(
                            stars = progress.totalStars,
                            onBack = { navigateBack() },
                            onSpeak = { text -> speechHelper.speak(text) },
                            onRecordQuizResult = { correct -> progressManager.recordQuizResult(correct) }
                        )
                    }

                    AppSection.RHYMES -> {
                        NurseryRhymesScreen(
                            stars = progress.totalStars,
                            onBack = { navigateBack() },
                            onSpeak = { text -> speechHelper.speak(text) },
                            onStopSpeech = { speechHelper.stop() }
                        )
                    }

                    AppSection.REWARDS -> {
                        RewardsScreen(
                            progress = progress,
                            onBack = { navigateBack() },
                            onSpeak = { text -> speechHelper.speak(text) }
                        )
                    }

                    AppSection.PROGRESS -> {
                        ProgressScreen(
                            progress = progress,
                            onResetAllData = { progressManager.resetAllData() },
                            onBack = { navigateBack() },
                            onSpeak = { text -> speechHelper.speak(text) }
                        )
                    }
                }
            }
        }
    }
}
