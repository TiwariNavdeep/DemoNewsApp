package com.example.demosample.presentation.uiState

import com.example.demosample.data.model.NewsModelDto
import com.example.demosample.domain.model.NewsModel

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val news: List<NewsModel>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}