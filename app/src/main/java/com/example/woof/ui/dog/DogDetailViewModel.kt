package com.example.woof.ui.dog

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.woof.data.DogRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DogDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val dogRepository: DogRepository
) : ViewModel() {

    val dogId: Int = checkNotNull(savedStateHandle[DogDetailDestination.dogIdArg])

    val uiState: StateFlow<DogDetailsUiState> =
        dogRepository.getDogStream(dogId)
            .filterNotNull()
            .map {
                DogDetailsUiState(it.toDogDetails())
            } //mapがマストだった理由は？⇒各要素をStateFlow型に変換する必要があったため
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = DogDetailsUiState()
            )

    suspend fun deleteDog() {
        dogRepository.deleteDog(uiState.value.dogDetail.toDog())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

data class DogDetailsUiState(
    val dogDetail: DogDetails = DogDetails()
)