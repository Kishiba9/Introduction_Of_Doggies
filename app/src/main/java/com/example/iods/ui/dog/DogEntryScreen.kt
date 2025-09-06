package com.example.iods.ui.dog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.iods.AppViewModelProvider
import com.example.iods.R
import com.example.iods.WoofTopAppBar
import com.example.iods.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object DogEntryDestination : NavigationDestination {
    override val route = "dog_entry"
    override val titleRes = R.string.dog_entry_title
}

@Composable
fun DogEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: DogEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            WoofTopAppBar(
                title = stringResource(DogEntryDestination.titleRes),
                canNavigateBack = canNavigateBack,
                canLogout = false,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        DogEntryBody(
            dogUiState = viewModel.dogUiState,
            onDogValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveDog()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
fun DogEntryBody(
    dogUiState: DogUiState,
    onDogValueChange: (DogDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        DogInputForm(
            dogDetails = dogUiState.dogDetails,
            onValueChange = onDogValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = dogUiState.isDogValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.dog_save_label))
        }
    }
}

@Composable
fun DogInputForm(
    dogDetails: DogDetails,
    modifier: Modifier = Modifier,
    onValueChange: (DogDetails) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = dogDetails.name,
            onValueChange = { onValueChange(dogDetails.copy(name = it)) },
            label = { Text(stringResource(R.string.dog_name_label)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = dogDetails.age,
            onValueChange = { onValueChange(dogDetails.copy(age = it)) },
            label = { Text(stringResource(R.string.dog_age_label)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = dogDetails.hobby,
            onValueChange = { onValueChange(dogDetails.copy(hobby = it)) },
            label = { Text(stringResource(R.string.dog_hobby_label)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
                value = dogDetails.freeComment ?: "",
                onValueChange = { onValueChange(dogDetails.copy(freeComment = it)) },
                label = { Text(stringResource(R.string.dog_freeComment_label)) },
                modifier = Modifier.fillMaxWidth(),
                enabled = enabled,
                singleLine = true
            )

//        //Kotlinでは、null許容型 (nullable types) の変数に安全にアクセスするために ?. (セーフコール) や let などの関数がよく使われます。dogDetails.freeComment は String? 型、つまり文字列または null を保持する可能性があります。
//        dogDetails.freeComment?.let {
//            OutlinedTextField(
//                value = it,
//                onValueChange = { onValueChange(dogDetails.copy(freeComment = it)) },
//                label = { Text(stringResource(R.string.dog_freeComment_label)) },
//                modifier = Modifier.fillMaxWidth(),
//                enabled = enabled,
//                singleLine = true
//            )
//        }

        if (enabled) {
            Text(
                text = stringResource(R.string.dog_required_field),
                modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_medium))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DogEntryScreenPreview() {
    DogEntryBody(
        dogUiState = DogUiState(
            dogDetails = DogDetails(
                name = "Dog name",
                age = "Age",
                hobby = "Hobby",
                freeComment = "Free comment"
            )
        ),
        onDogValueChange = {},
        onSaveClick = {}
    )
}
