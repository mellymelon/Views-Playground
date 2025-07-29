package com.example.viewsplayground.flowerList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewsplayground.data.Flower
import com.example.viewsplayground.data.FlowerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FlowerListViewModel @Inject internal constructor(private val flowerRepository: FlowerRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(FlowerListUiState.Success(emptyList()))
    val uiState: StateFlow<FlowerListUiState> = _uiState

    init {
        viewModelScope.launch {
            flowerRepository.getFlowers().collect { flowers ->
                _uiState.value = FlowerListUiState.Success(flowers)
            }
        }
    }

    fun insertFlower(flower: Flower) = viewModelScope.launch {
        flowerRepository.add(flower)
    }

    fun getFlowerById(id: Long): Flow<Flower> {
        return flowerRepository.getFlower(id)
    }

    fun removeFlower(id: Long) = viewModelScope.launch {
        flowerRepository.delete(id)
    }
}

sealed class FlowerListUiState {
    data class Success(val flowers: List<Flower>) : FlowerListUiState()
    data class Error(val exception: Throwable) : FlowerListUiState()
}