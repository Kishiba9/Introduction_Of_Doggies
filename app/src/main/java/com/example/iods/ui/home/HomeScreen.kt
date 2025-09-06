package com.example.iods.ui.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.iods.AppViewModelProvider
import com.example.iods.R
import com.example.iods.WoofTopAppBar
import com.example.iods.data.Dog
import com.example.iods.ui.navigation.NavigationDestination
import com.example.iods.ui.theme.WoofTheme

object HomeDestination : NavigationDestination {
    override val route = "home"

    override val titleRes = R.string.app_name
}

@Composable
fun HomeScreen(
    navigateToDogEntry: () -> Unit,
    navigateToDogUpdate: (Int) -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
    ) {

    val homeUiState by viewModel.homeUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            WoofTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                canLogout = true,
                onLogoutClick = onLogout,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToDogEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(
                        end = WindowInsets.safeDrawing.asPaddingValues()
                            .calculateEndPadding(LocalLayoutDirection.current)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.dog_entry_title)
                )
            }
        }
    ) { innerPadding ->
        HomeBody(
            dogList = homeUiState.dogList,
            onDogClick = navigateToDogUpdate,
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding,
        )
    }
}

@Composable
private fun HomeBody (
    dogList: List<Dog>,
    onDogClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
    ) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if(dogList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_dog_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding)
            )
        } else {
            DoggyList(
                dogList = dogList,
                onDogClick = { onDogClick(it.id) },
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_small))
            )
        }
    }
}

@Composable
private fun DoggyList(
    dogList: List<Dog>,
    onDogClick: (Dog) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = dogList, key = {it.id}) { dog ->
            DoggyItem(
                dog = dog,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onDogClick(dog) }
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
            )
        }
    }
}

@Composable
private fun DoggyItem(
    dog: Dog,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        var expanded by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_small))
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(dimensionResource(id = R.dimen.padding_medium))
        ) {
            Text(
                text = dog.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium))
            )
            Spacer(Modifier.weight(1f))

            DoggyIconButton(
                expanded = expanded,
                onClickButton = {expanded = !expanded}
            )
            }
        }

        if(expanded) {
            DoggyHobby(
                dog = dog,
                modifier = Modifier.padding(
                    start = dimensionResource(R.dimen.padding_large),
                    end = dimensionResource(R.dimen.padding_medium),
                    top = dimensionResource(R.dimen.padding_small),
                    bottom = dimensionResource(R.dimen.padding_medium)
                )
            )
        }
    }
}

@Composable
fun DoggyHobby(
    dog: Dog,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.about),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = dog.hobby,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun DoggyIconButton(
    expanded: Boolean,
    onClickButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClickButton,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(R.string.expand_button_content_description),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
    WoofTheme {
        HomeBody(listOf(
            Dog(1, "Kota", "3", "Eating Food", "", ""), Dog(2, "Baby", "1", "Playing with his Toy", null)
        ), onDogClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyEmptyListPreview() {
    WoofTheme {
        HomeBody(listOf(), onDogClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun DogItemPreview() {
    WoofTheme {
        DoggyItem(
            Dog(1, "Kota", "3", "Eating food", "", null)
        )
    }
}