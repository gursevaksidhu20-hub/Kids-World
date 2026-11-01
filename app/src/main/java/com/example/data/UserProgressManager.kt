package com.example.data

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import com.example.model.Badge
import com.example.model.SavedArtwork
import com.example.model.UserProgress
import org.json.JSONArray
import org.json.JSONObject

class UserProgressManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("kids_world_data", Context.MODE_PRIVATE)

    var progressState = mutableStateOf(loadProgress())
        private set

    var savedArtworks = mutableStateOf(loadArtworks())
        private set

    private fun loadProgress(): UserProgress {
        val stars = prefs.getInt("total_stars", 15) // Start with 15 welcoming starter stars
        val activities = prefs.getStringSet("completed_activities", emptySet()) ?: emptySet()
        val favs = prefs.getStringSet("favorite_words", emptySet()) ?: emptySet()
        val coloring = prefs.getStringSet("completed_coloring", emptySet()) ?: emptySet()
        val quizzes = prefs.getInt("quizzes_taken", 0)
        val correct = prefs.getInt("quiz_correct", 0)
        val letters = (prefs.getStringSet("letters_explored", emptySet()) ?: emptySet())
            .mapNotNull { it.firstOrNull() }.toSet()
        val numbers = (prefs.getStringSet("numbers_explored", emptySet()) ?: emptySet())
            .mapNotNull { it.toIntOrNull() }.toSet()
        val words = prefs.getStringSet("words_explored", emptySet()) ?: emptySet()
        val badges = prefs.getStringSet("unlocked_badges", setOf("welcome")) ?: setOf("welcome")

        return UserProgress(
            totalStars = stars,
            completedActivities = activities,
            favoriteWords = favs,
            completedColoringPages = coloring,
            quizzesTaken = quizzes,
            quizCorrectAnswers = correct,
            lettersExplored = letters,
            numbersExplored = numbers,
            wordsExplored = words,
            unlockedBadges = badges
        )
    }

    private fun saveProgress(progress: UserProgress) {
        progressState.value = progress
        prefs.edit()
            .putInt("total_stars", progress.totalStars)
            .putStringSet("completed_activities", progress.completedActivities)
            .putStringSet("favorite_words", progress.favoriteWords)
            .putStringSet("completed_coloring", progress.completedColoringPages)
            .putInt("quizzes_taken", progress.quizzesTaken)
            .putInt("quiz_correct", progress.quizCorrectAnswers)
            .putStringSet("letters_explored", progress.lettersExplored.map { it.toString() }.toSet())
            .putStringSet("numbers_explored", progress.numbersExplored.map { it.toString() }.toSet())
            .putStringSet("words_explored", progress.wordsExplored)
            .putStringSet("unlocked_badges", progress.unlockedBadges)
            .apply()
    }

    fun addStars(amount: Int) {
        val current = progressState.value
        val updatedStars = current.totalStars + amount
        val newBadges = checkNewBadges(updatedStars, current.unlockedBadges)
        saveProgress(current.copy(totalStars = updatedStars, unlockedBadges = newBadges))
    }

    fun recordLetterExplored(letter: Char) {
        val current = progressState.value
        val set = current.lettersExplored + letter
        val starsBonus = if (set.size % 5 == 0 && !current.lettersExplored.contains(letter)) 2 else 0
        val updatedStars = current.totalStars + starsBonus
        val badges = checkNewBadges(updatedStars, current.unlockedBadges).toMutableSet()
        if (set.size >= 26) badges.add("abc_champion")
        saveProgress(current.copy(lettersExplored = set, totalStars = updatedStars, unlockedBadges = badges))
    }

    fun recordNumberExplored(num: Int) {
        val current = progressState.value
        val set = current.numbersExplored + num
        val starsBonus = if (set.size % 10 == 0 && !current.numbersExplored.contains(num)) 2 else 0
        val updatedStars = current.totalStars + starsBonus
        val badges = checkNewBadges(updatedStars, current.unlockedBadges).toMutableSet()
        if (set.size >= 50) badges.add("number_wizard")
        saveProgress(current.copy(numbersExplored = set, totalStars = updatedStars, unlockedBadges = badges))
    }

    fun recordWordExplored(word: String) {
        val current = progressState.value
        val set = current.wordsExplored + word
        saveProgress(current.copy(wordsExplored = set))
    }

    fun recordQuizResult(correct: Boolean) {
        val current = progressState.value
        val totalQ = current.quizzesTaken + 1
        val correctQ = current.quizCorrectAnswers + if (correct) 1 else 0
        val bonus = if (correct) 2 else 1
        val updatedStars = current.totalStars + bonus
        val badges = checkNewBadges(updatedStars, current.unlockedBadges).toMutableSet()
        if (correctQ >= 5) badges.add("quiz_star")
        if (correctQ >= 15) badges.add("super_thinker")
        saveProgress(
            current.copy(
                quizzesTaken = totalQ,
                quizCorrectAnswers = correctQ,
                totalStars = updatedStars,
                unlockedBadges = badges
            )
        )
    }

    fun recordColoringPageCompleted(pageId: String) {
        val current = progressState.value
        val set = current.completedColoringPages + pageId
        val updatedStars = current.totalStars + 3
        val badges = checkNewBadges(updatedStars, current.unlockedBadges).toMutableSet()
        badges.add("coloring_artist")
        if (set.size >= 10) badges.add("master_artist")
        saveProgress(
            current.copy(
                completedColoringPages = set,
                totalStars = updatedStars,
                unlockedBadges = badges
            )
        )
    }

    fun toggleFavoriteWord(word: String) {
        val current = progressState.value
        val set = if (current.favoriteWords.contains(word)) {
            current.favoriteWords - word
        } else {
            current.favoriteWords + word
        }
        saveProgress(current.copy(favoriteWords = set))
    }

    private fun checkNewBadges(stars: Int, currentBadges: Set<String>): Set<String> {
        val badges = currentBadges.toMutableSet()
        if (stars >= 20) badges.add("star_collector")
        if (stars >= 50) badges.add("super_learner")
        if (stars >= 100) badges.add("kids_world_hero")
        return badges
    }

    // Artwork persistence
    private fun loadArtworks(): List<SavedArtwork> {
        val json = prefs.getString("saved_artworks_json", "[]") ?: "[]"
        val list = mutableListOf<SavedArtwork>()
        try {
            val arr = JSONArray(json)
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                val id = obj.getString("id")
                val title = obj.getString("title")
                val timestamp = obj.getLong("timestamp")
                val isColoring = obj.optBoolean("isColoring", true)
                val pageId = if (obj.has("pageId")) obj.getString("pageId") else null
                val previewEmoji = obj.optString("previewEmoji", "🎨")
                val colorMap = mutableMapOf<String, Long>()
                if (obj.has("regionColors")) {
                    val colorsObj = obj.getJSONObject("regionColors")
                    val keys = colorsObj.keys()
                    while (keys.hasNext()) {
                        val key = keys.next()
                        colorMap[key] = colorsObj.getLong(key)
                    }
                }
                list.add(
                    SavedArtwork(
                        id = id,
                        title = title,
                        timestamp = timestamp,
                        isColoring = isColoring,
                        pageId = pageId,
                        regionColors = colorMap,
                        previewEmoji = previewEmoji
                    )
                )
            }
        } catch (e: Exception) {
            // ignore
        }
        return list
    }

    fun saveArtwork(artwork: SavedArtwork) {
        val updated = (listOf(artwork) + savedArtworks.value.filter { it.id != artwork.id }).take(30)
        savedArtworks.value = updated
        val arr = JSONArray()
        for (item in updated) {
            val obj = JSONObject()
            obj.put("id", item.id)
            obj.put("title", item.title)
            obj.put("timestamp", item.timestamp)
            obj.put("isColoring", item.isColoring)
            if (item.pageId != null) obj.put("pageId", item.pageId)
            obj.put("previewEmoji", item.previewEmoji)
            val colorsObj = JSONObject()
            for ((k, v) in item.regionColors) {
                colorsObj.put(k, v)
            }
            obj.put("regionColors", colorsObj)
            arr.put(obj)
        }
        prefs.edit().putString("saved_artworks_json", arr.toString()).apply()
        addStars(2)
    }

    fun deleteArtwork(id: String) {
        val updated = savedArtworks.value.filter { it.id != id }
        savedArtworks.value = updated
        val arr = JSONArray()
        for (item in updated) {
            val obj = JSONObject()
            obj.put("id", item.id)
            obj.put("title", item.title)
            obj.put("timestamp", item.timestamp)
            obj.put("isColoring", item.isColoring)
            if (item.pageId != null) obj.put("pageId", item.pageId)
            obj.put("previewEmoji", item.previewEmoji)
            val colorsObj = JSONObject()
            for ((k, v) in item.regionColors) {
                colorsObj.put(k, v)
            }
            obj.put("regionColors", colorsObj)
            arr.put(obj)
        }
        prefs.edit().putString("saved_artworks_json", arr.toString()).apply()
    }

    fun resetAllData() {
        prefs.edit().clear().apply()
        progressState.value = UserProgress(totalStars = 15, unlockedBadges = setOf("welcome"))
        savedArtworks.value = emptyList()
    }
}
