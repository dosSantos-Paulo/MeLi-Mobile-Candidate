package com.dossantos.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dossantos.data.model.suggestions.SuggestionsEntity

@Dao
interface MeLiSuggestionsDao {

    @Query("SELECT * FROM meli_suggestions ORDER BY dateTimeMills DESC LIMIT 4")
    fun getSuggestions(): List<SuggestionsEntity>

    @Insert
    fun insertSuggestion(suggestionsEntity: SuggestionsEntity)
}
