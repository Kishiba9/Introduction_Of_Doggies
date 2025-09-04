package com.example.woof.ui.dog

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.woof.AppViewModelProvider
import com.example.woof.R
import com.example.woof.WoofTopAppBar
import com.example.woof.data.Dog
import com.example.woof.ui.navigation.NavigationDestination
import com.example.woof.ui.theme.WoofTheme
import kotlinx.coroutines.launch

object DogDetailDestination : NavigationDestination {
    override val route = "dog_detail"

    override val titleRes = R.string.dog_detail

    const val dogIdArg: String = "dogId"

    val routeWithArgs = "$route/{$dogIdArg}"
}

@Composable
fun DogDetailScreen(
    navigateToEditDog: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DogDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            WoofTopAppBar(
                title = stringResource(DogDetailDestination.titleRes),
                canNavigateBack = true,
                canLogout = false,
                navigateUp = navigateBack
            )
        },
        floatingActionButton =
            {
                FloatingActionButton(
                    onClick = { navigateToEditDog(uiState.value.dogDetail.id) },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .padding(end = WindowInsets.safeDrawing.asPaddingValues()
                            .calculateEndPadding(LocalLayoutDirection.current)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.dog_edit)
                    )
                }
            },
        modifier = modifier
    ) { innerPadding ->
        DogDetailBody(
            dogDetailUiState = uiState.value,
            onDelete = {
                coroutineScope.launch {
                    viewModel.deleteDog()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(
                start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                top = innerPadding.calculateTopPadding(),
                end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
            )
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
private fun DogDetailBody(
    dogDetailUiState: DogDetailsUiState,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

        DogDetail(dog = dogDetailUiState.dogDetail.toDog(), modifier = Modifier.fillMaxWidth())

        OutlinedButton(
            onClick = { deleteConfirmationRequired = true },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.delete_dog))
        }

        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDelete()
                },
                onDeleteCancel = { deleteConfirmationRequired = false },
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
            )
        }
    }
}
@Composable
fun DogDetail(
    dog: Dog,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
       Column(
           modifier = Modifier
               .fillMaxWidth()
               .padding(dimensionResource(R.dimen.padding_medium)),
           verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
       ) {
           DogDetailRows(
               labelResourceId = R.string.dog_name_label,
               value = dog.name,
               modifier = Modifier.padding(
                   horizontal = dimensionResource(R.dimen.padding_medium)
               )
           )

           DogDetailRows(
               labelResourceId = R.string.dog_age_label,
               value = dog.age,
               modifier = Modifier.padding(
                   horizontal = dimensionResource(R.dimen.padding_medium)
               )
           )

           DogDetailRows(
               labelResourceId = R.string.dog_hobby_label,
               value = dog.hobby,
               modifier = Modifier.padding(
                   horizontal = dimensionResource(R.dimen.padding_medium)
               )
           )

           dog.freeComment?.let{
               DogDetailRows(
                   labelResourceId = R.string.dog_freeComment_label,
                   value = dog.freeComment,
                   modifier = Modifier.padding(
                       horizontal = dimensionResource(R.dimen.padding_medium)
                   )
               )
           }
       }
    }
}

@Composable
private fun DogDetailRows(
    @StringRes labelResourceId: Int,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(text = stringResource(id = labelResourceId))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = value)
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(stringResource(R.string.attention))},
        text = { Text(stringResource(R.string.delete_question)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.No))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.Yes))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DogDetailScreenPreview() {
    WoofTheme {
        val dogDetailTestUiState = DogDetailsUiState(
            dogDetail = DogDetails(
                id = 1,
                name = stringResource(R.string.dog_name_1),
                age = "5",
                hobby = stringResource(R.string.dog_description_1),
                "こんにちは" )
        )
        DogDetailBody(
            dogDetailUiState = dogDetailTestUiState,
            onDelete = {}
        )
    }
}

