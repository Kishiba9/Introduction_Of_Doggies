package com.example.iods.ui.dog

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.iods.AppViewModelProvider
import com.example.iods.R
import com.example.iods.WoofTopAppBar
import com.example.iods.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object DogEditDestination : NavigationDestination {

    override val route = "dogEdit"

    override val titleRes = R.string.dog_edit

    const val dogIdArg: String = "dogId"

    val routeWithArgs = "$route/{$dogIdArg}"
}

@Composable
fun DogEditScreen (
    onNavigateUp: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DogEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            WoofTopAppBar(
                title = stringResource(R.string.dog_edit),
                canNavigateBack = true,
                canLogout = false,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        DogEntryBody(
            dogUiState = viewModel.dogUiState,
            onDogValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateDog()
                    onNavigateBack()
                }
            },
            modifier = Modifier.padding(
                start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                top = innerPadding.calculateTopPadding(),
                end = innerPadding.calculateEndPadding(LocalLayoutDirection.current)
            )
                .verticalScroll(rememberScrollState())
        )
    }
}