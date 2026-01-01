package com.example.demosample.presentation.viewModel

import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.demosample.domain.enums.NewsCategory
import com.example.demosample.domain.model.NewsModel
import com.example.demosample.domain.useCase.GetNewsUseCase
import com.example.demosample.utils.network.NetworkObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
    class HomeViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val networkObserver: NetworkObserver,
    ) : ViewModel() {

    // Convert LiveData to StateFlow for Compose
    val isOnline = networkObserver.isConnected.asFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    init {
        // Start observing when ViewModel is created
        networkObserver.startNetworkCallback()
    }

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
    override fun onCleared() {
        super.onCleared()
        // Important: Stop to prevent memory leaks
        networkObserver.stopNetworkCallback()
    }
}