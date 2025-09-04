package com.example.woof.ui.dog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.woof.data.DogRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DogEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val dogRepository: DogRepository
) : ViewModel() {
    //SaveStateHandleは、画面遷移時に渡されたデータ（例：dogId）を失わずに保持するためのメカニズム

    var dogUiState by mutableStateOf(DogUiState())
        private set

    val dogId: Int = checkNotNull(savedStateHandle[DogEditDestination.dogIdArg])

    init {
        viewModelScope.launch {
            dogUiState = dogRepository.getDogStream(dogId)
                .filterNotNull()
                .first()
                .toDogUiState(true)
        }
    }

    //なぜupdateDogに引数は入れない？suspendをつけてるから？
    suspend fun updateDog() {
        if (validateInput(dogUiState.dogDetails)) {
            dogRepository.updateDog(dogUiState.dogDetails.toDog())
        }
    }

    fun updateUiState (dogDetails: DogDetails) {
        dogUiState =
            DogUiState(dogDetails = dogDetails, isDogValid = validateInput(dogDetails))
    }

    private fun validateInput (uiState: DogDetails = dogUiState.dogDetails) : Boolean {
        return with(uiState) {
            uiState.name.isNotBlank() && uiState.age.isNotBlank() && uiState.hobby.isNotBlank()
        }
    }
}