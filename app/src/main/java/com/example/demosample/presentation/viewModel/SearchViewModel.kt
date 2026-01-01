package com.example.demosample.presentation.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.demosample.domain.useCase.SearchNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
    class SearchViewModel @Inject constructor(
        private val searchNewsUseCase: SearchNewsUseCase
    ) : ViewModel() {

    var query by mutableStateOf("")
        private set

    val searchResult = snapshotFlow { query }
        .filter { it.isNotBlank() }
        .debounce(500)
        .distinctUntilChanged()
        .flatMapLatest { q ->
            searchNewsUseCase(q)
        }
        .cachedIn(viewModelScope)

    fun onQueryChange(newQuery: String) {
        query = newQuery
    }
}