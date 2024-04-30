package com.dossantos.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dossantos.data.model.suggestions.SuggestionsEntity

@Database(entities = [SuggestionsEntity::class], version = 1, exportSchema = false)
abstract class MeLiSuggestionDataBase: RoomDatabase() {
    abstract fun getSuggestionsDao(): MeLiSuggestionsDao
}
