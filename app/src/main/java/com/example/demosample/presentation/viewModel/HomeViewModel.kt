package com.example.demosample.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.demosample.domain.enums.NewsCategory
import com.example.demosample.domain.model.NewsModel
import com.example.demosample.domain.useCase.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
    class HomeViewModel @Inject constructor(
        private val getNewsUseCase: GetNewsUseCase
    ) : ViewModel() {

    private val _homeUIState: MutableStateFlow<PagingData<NewsModel>> = MutableStateFlow(value = PagingData.empty())
    val homeUIState: MutableStateFlow<PagingData<NewsModel>> get() = _homeUIState

    suspend fun getNews(category: NewsCategory) {
        getNewsUseCase(category)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                _homeUIState.value = it
            }

    }
}