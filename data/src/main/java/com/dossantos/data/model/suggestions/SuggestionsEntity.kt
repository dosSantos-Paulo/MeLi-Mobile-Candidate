package com.dossantos.data.model.suggestions

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meli_suggestions")
data class SuggestionsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val categoryId: String,
    val dateTimeMills: Long,
    val suggestionType: String
)
