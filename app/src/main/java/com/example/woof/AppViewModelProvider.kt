package com.example.woof

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.woof.ui.dog.DogDetailViewModel
import com.example.woof.ui.dog.DogEditViewModel
import com.example.woof.ui.dog.DogEntryViewModel
import com.example.woof.ui.home.HomeViewModel
import okhttp3.Credentials

object AppViewModelProvider {
    val Factory = viewModelFactory {

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(
                dogApplication().container.dogRepository
            )
        }

        // Initializer for DogEditViewModel
        initializer {
            DogEditViewModel(
                savedStateHandle = createSavedStateHandle(),
                dogApplication().container.dogRepository
            )
        }

        // Initializer for DogEntryViewModel
        initializer {
            DogEntryViewModel(
                dogApplication().container.dogRepository
            )
        }

        // Initializer for DogDetailViewModel
        initializer {
            DogDetailViewModel(
                savedStateHandle = createSavedStateHandle(),
                dogApplication().container.dogRepository
            )
        }
    }
}

fun CreationExtras.dogApplication(): DogApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DogApplication)