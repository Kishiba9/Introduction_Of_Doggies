package com.example.iods.ui.dog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.iods.data.Dog
import com.example.iods.data.DogRepository

class DogEntryViewModel(private val dogRepository: DogRepository) : ViewModel() {

//    private var _uiState = MutableStateFlow(DogUiState())
//    val uiState: StateFlow<DogUiState> = _uiState.asStateFlow()

    var dogUiState: DogUiState by mutableStateOf(DogUiState())
        private set

//    fun loadDogDetails(id: Int) {
//        if (id == 0) {
//            _uiState.value = DogUiState()
//            return
//        }
//        viewModelScope.launch {
//            dogRepository.getDogStream(id).collect { dog ->
//                _uiState.update { currentState ->
//                    dog?.let {
//                        DogUiState(dogDetails = it.toDogDetails(), isDogValid = validateDogDetails(it.toDogDetails()))
//                    } ?: DogUiState(dogDetails = DogDetails(), isDogValid = false)
//                }
//            }
//        }
//    }

    fun updateUiState(dogDetails: DogDetails) {
        dogUiState =
            DogUiState(dogDetails = dogDetails, isDogValid = validateInput(dogDetails))
        }

    //新しいデータベースに保存する
    suspend fun saveDog() {
        if(validateInput()) {
            dogRepository.insertDog(dogUiState.dogDetails.toDog())
        }
    }

//既存の犬を更新する
//    fun updateDog(dogDetail: DogDetails) {
//        viewModelScope.launch {
//            dogRepository.updateDog(dogDetail.toDog())
//        }
//    }

    //犬をデータベースから削除する
//    suspend fun deleteDog() {
//        dogRepository.deleteDog(dogUiState.dogDetails.toDog())
//    }

    private fun validateInput(uiState: DogDetails = dogUiState.dogDetails): Boolean {
        return with(uiState) {
            id.toString().isNotBlank() && name.isNotBlank() && age.isNotBlank()
        }
    }
}

    data class DogUiState(
        val dogDetails: DogDetails = DogDetails(),
        val isDogValid: Boolean = false
    )

    data class DogDetails(
        val id: Int = 0,
        val name: String = "",
        val age: String = "",
        val hobby: String = "",
        val freeComment: String? = "",
        val imageUri: String? = null
    )

    fun Dog.toDogUiState(isDogValid: Boolean = false): DogUiState = DogUiState(
        dogDetails = this.toDogDetails(),
        isDogValid = isDogValid
    )

    fun Dog.toDogDetails(): DogDetails = DogDetails(
        id = this.id,
        name = this.name,
        age = this.age,
        hobby = this.hobby,
        freeComment = this.freeComment,
        imageUri = this.imageUri
    )

    fun DogDetails.toDog(): Dog = Dog(
        id = this.id,
        name = this.name,
        age = this.age,
        hobby = this.hobby,
        freeComment = this.freeComment,
        imageUri = this.imageUri
    )


