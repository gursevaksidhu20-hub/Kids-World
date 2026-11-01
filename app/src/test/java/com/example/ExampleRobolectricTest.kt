package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.KidsColoringData
import com.example.data.KidsQuizData
import com.example.data.KidsRepository
import com.example.data.UserProgressManager
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context matches Kids World`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Kids World", appName)
    }

    @Test
    fun `verify alphabets contains all 26 letters`() {
        val alphabets = KidsRepository.alphabets
        assertEquals(26, alphabets.size)
        assertEquals('A', alphabets.first().letter)
        assertEquals("Apple", alphabets.first().word)
        assertEquals('Z', alphabets.last().letter)
        assertEquals("Zebra", alphabets.last().word)
    }

    @Test
    fun `verify numbers contains 1 to 100`() {
        val numbers = KidsRepository.numbers
        assertEquals(100, numbers.size)
        assertEquals(1, numbers.first().value)
        assertEquals("One", numbers.first().englishWord)
        assertEquals(100, numbers.last().value)
        assertEquals("One hundred", numbers.last().englishWord)
    }

    @Test
    fun `verify animals collection has 40 animals`() {
        val animals = KidsRepository.animals
        assertTrue(animals.size >= 40)
        assertTrue(animals.any { it.category == "Wild" })
        assertTrue(animals.any { it.category == "Farm" })
        assertTrue(animals.any { it.category == "Pets" })
        assertTrue(animals.any { it.category == "Sea" })
    }

    @Test
    fun `verify colors collection has 16 colors`() {
        val colors = KidsRepository.colors
        assertEquals(16, colors.size)
    }

    @Test
    fun `verify coloring pages has at least 30 pages`() {
        val pages = KidsColoringData.pages
        assertTrue(pages.size >= 30)
        pages.forEach { page ->
            assertTrue(page.regions.isNotEmpty())
        }
    }

    @Test
    fun `verify vocabulary has 150 plus words`() {
        val vocab = KidsRepository.vocabulary
        assertTrue(vocab.size >= 150)
    }

    @Test
    fun `verify progress manager saves and adds stars`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val manager = UserProgressManager(context)
        val initialStars = manager.progressState.value.totalStars
        manager.addStars(5)
        assertEquals(initialStars + 5, manager.progressState.value.totalStars)
    }
}
