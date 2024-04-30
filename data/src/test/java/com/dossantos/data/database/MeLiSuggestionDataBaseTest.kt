package com.dossantos.data.database

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.dossantos.data.model.suggestions.SuggestionsEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

// Test not working
@RunWith(RobolectricTestRunner::class)
class MeLiSuggestionDataBaseTest {

    private lateinit var database: MeLiSuggestionDataBase
    private lateinit var suggestionsDao: MeLiSuggestionsDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, MeLiSuggestionDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        suggestionsDao = database.getSuggestionsDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testInsertAndGetSuggestion() = runBlocking {
        // Given
        val suggestion = SuggestionsEntity(
            categoryId = "categoryId",
            dateTimeMills = 12345L,
            suggestionType = "suggestionType"
        )

        // When
        suggestionsDao.insertSuggestion(suggestion)
        suggestionsDao.getSuggestions().first().let { suggestion ->

            // Then
            assert(suggestion.categoryId == "categoryId")
        }

    }
}
