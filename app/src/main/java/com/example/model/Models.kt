package com.example.model

import androidx.compose.ui.graphics.Color

enum class AppSection(val title: String, val icon: String, val colorHex: Long) {
    HOME("Home", "🏠", 0xFF70C8FF),
    ABC("ABC Learning", "🔤", 0xFFFF6B6B),
    NUMBERS("Numbers 1–100", "🔢", 0xFFFFB703),
    COLORING("Coloring Book", "🎨", 0xFF4D96FF),
    DRAWING("Free Drawing", "✏️", 0xFF8FE3B0),
    ANIMALS("Animals", "🦁", 0xFFFF9E00),
    COLORS("Colors", "🌈", 0xFF9D4EDD),
    WORDS("English Words", "📖", 0xFF38A3E5),
    FRUITS_VEGGIES("Fruits & Veggies", "🍎", 0xFF6BCB77),
    SHAPES_BIRDS("Shapes & Birds", "⭐", 0xFFFF8FA3),
    WRITING("Writing Practice", "✍️", 0xFF48CAE4),
    GAMES("Educational Games", "🎮", 0xFFFFD166),
    RHYMES("Nursery Rhymes", "🎵", 0xFFC8B6FF),
    REWARDS("My Rewards", "🏆", 0xFFFFB703),
    PROGRESS("My Progress", "📊", 0xFF52B788)
}

data class AlphabetItem(
    val letter: Char,
    val word: String,
    val hindiMeaning: String,
    val hindiPronunciation: String,
    val phonics: String,
    val emoji: String,
    val exampleSentence: String,
    val funFact: String
)

data class NumberItem(
    val value: Int,
    val englishWord: String,
    val hindiWord: String,
    val hindiPronunciation: String,
    val objectEmoji: String,
    val category: String = if (value <= 10) "1–10" else if (value <= 20) "11–20" else if (value <= 50) "21–50" else "51–100"
)

data class AnimalItem(
    val id: String,
    val name: String,
    val hindiName: String,
    val category: String, // Farm, Wild, Pets, Sea
    val emoji: String,
    val sound: String,
    val funFact: String,
    val food: String
)

data class ColorItem(
    val name: String,
    val hindiName: String,
    val color: Color,
    val hexString: String,
    val examples: List<String>,
    val emojiExample: String
)

data class WordItem(
    val word: String,
    val category: String,
    val hindiMeaning: String,
    val emoji: String,
    val examplePhrase: String
)

data class FruitVegItem(
    val name: String,
    val isFruit: Boolean, // true = Fruit, false = Vegetable
    val hindiName: String,
    val emoji: String,
    val color: String,
    val funFact: String
)

data class ShapeItem(
    val name: String,
    val hindiName: String,
    val sides: Int,
    val emoji: String,
    val funFact: String
)

data class BirdInsectItem(
    val name: String,
    val isBird: Boolean, // true = Bird, false = Insect
    val hindiName: String,
    val emoji: String,
    val funFact: String
)

// Real interactive coloring region model
data class ColoringRegion(
    val id: String,
    val label: String,
    // Normalized polygon points (0f..1f) representing the enclosed region
    val polygon: List<Pair<Float, Float>>,
    // Center point for tapping/labeling
    val centerX: Float,
    val centerY: Float,
    val defaultColor: Long = 0xFFFFFFFF
)

data class ColoringPage(
    val id: String,
    val title: String,
    val category: String, // Animals, Birds, Sea Animals, Dinosaurs, Fruits, Vehicles, Space, Shapes
    val emoji: String,
    val regions: List<ColoringRegion>,
    // Optional outline lines or features (normalized coordinates)
    val decorativeLines: List<List<Pair<Float, Float>>> = emptyList()
)

data class DrawingStroke(
    val points: List<Pair<Float, Float>>,
    val color: Long,
    val strokeWidth: Float,
    val isEraser: Boolean = false
)

data class StampPlacement(
    val emoji: String,
    val x: Float,
    val y: Float,
    val size: Float
)

data class SavedArtwork(
    val id: String,
    val title: String,
    val timestamp: Long,
    val isColoring: Boolean,
    val pageId: String? = null,
    val regionColors: Map<String, Long> = emptyMap(),
    val previewEmoji: String = "🎨"
)

data class RhymeItem(
    val title: String,
    val emoji: String,
    val durationSeconds: Int,
    val lines: List<String>
)

data class QuizQuestion(
    val id: String,
    val type: String, // ALPHABET, NUMBER, ANIMAL, COLOR, PICTURE, MISSING_LETTER, COUNTING, SHAPE
    val prompt: String,
    val visualHint: String,
    val options: List<String>,
    val correctIndex: Int,
    val encouragement: String
)

data class Badge(
    val id: String,
    val name: String,
    val description: String,
    val emoji: String,
    val requiredStars: Int,
    val category: String
)

data class UserProgress(
    val totalStars: Int = 15,
    val completedActivities: Set<String> = emptySet(),
    val favoriteWords: Set<String> = emptySet(),
    val completedColoringPages: Set<String> = emptySet(),
    val quizzesTaken: Int = 0,
    val quizCorrectAnswers: Int = 0,
    val lettersExplored: Set<Char> = emptySet(),
    val numbersExplored: Set<Int> = emptySet(),
    val wordsExplored: Set<String> = emptySet(),
    val unlockedBadges: Set<String> = setOf("welcome")
)
