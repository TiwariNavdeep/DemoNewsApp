package com.example.demosample.presentation.uiState

import com.example.demosample.data.model.NewsModelDto

sealed class SearchUiState {
    object Loading : SearchUiState()
    data class Success(val news: List<NewsModelDto>) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}