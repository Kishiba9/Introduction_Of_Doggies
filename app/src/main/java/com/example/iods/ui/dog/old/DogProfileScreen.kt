//package com.example.woof.ui.dog.old
//
//import android.app.Application
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.aspectRatio
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.Card
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.dimensionResource
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.lifecycle.viewmodel.compose.viewModel
//import coil.compose.AsyncImage
//import coil.request.ImageRequest
//import com.example.woof.R
//
//@Composable
//fun EachProfileScreen (
//    dogId: Int?,
//    //dogList: List<Dog>,
//    viewModel: WoofViewModel = viewModel(
//        factory = WoofViewModelFactory(LocalContext.current.applicationContext as Application)
//    ),
//    modifier: Modifier = Modifier
//) {
//
//    val uiState by viewModel.uiState.collectAsState()
//    val dog = uiState.dogDetails
//
//    LaunchedEffect(dogId) {
//        if(dogId != null) {
//            viewModel.loadDogDetails(dogId)
//        }
//    }
//
//    if (dog.id != 0) {
//        Card (
//            modifier = modifier
//                .fillMaxSize()
//                .padding(dimensionResource(R.dimen.padding_medium))
//        ){
//            Column (
//                modifier = Modifier
//                    .verticalScroll(rememberScrollState())
//                    .padding(dimensionResource(R.dimen.padding_medium))
//            ) {
//                Text(
//                    text = "Profile",
//                    style = MaterialTheme.typography.displayMedium,
//                    modifier = Modifier.padding(
//                        top = dimensionResource(R.dimen.padding_small),
//                        bottom = dimensionResource(R.dimen.padding_small)
//                    )
//                )
//                AsyncImage(
//                    model = ImageRequest.Builder(LocalContext.current)
//                        .data(dog.imageUri) // Dog オブジェクトの imageUri を使用
//                        .crossfade(true)
//                        .build(),
//                    contentDescription = null, // 必要に応じて適切な contentDescription を設定してください
//                    contentScale = ContentScale.Crop,
//                    error = painterResource(R.drawable.nox), // 画像ロード失敗時のプレースホルダー
//                    placeholder = painterResource(R.drawable.nox), // 画像ロード中のプレースホルダー
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .aspectRatio(17f/12f)
//                )
//
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(
//                            top = dimensionResource(R.dimen.padding_medium),
//                            start = dimensionResource(R.dimen.padding_medium)
//                        )
//                ) {
//                    Text(
//                        text = "Name",
//                        style = MaterialTheme.typography.bodyLarge,
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium))
//                    )
//                    Text(
//                        text = dog.name,
//                        style = MaterialTheme.typography.bodyMedium,
//                        modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_small))
//                    )
//
//                    Text(
//                        text = "Age",
//                        style = MaterialTheme.typography.bodyLarge,
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium))
//                    )
//
//                    Text(
//                        text = dog.age.toString() + " years old",
//                        style = MaterialTheme.typography.bodyMedium,
//                        modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_small))
//                    )
//
//                    Text(
//                        text = "Hobby",
//                        style = MaterialTheme.typography.bodyLarge,
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium))
//                    )
//
//                    Text(
//                        text = dog.hobby,
//                        style = MaterialTheme.typography.bodyMedium,
//                        modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_small))
//                    )
//                    dog.freeComment?.let {
//                        if (it.isNotBlank()) {
//                            Text(
//                                text = "Comment",
//                                style = MaterialTheme.typography.bodyLarge,
//                                fontWeight = FontWeight.Bold,
//                                modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_medium))
//                            )
//                            Text(
//                                text = it,
//                                style = MaterialTheme.typography.bodyMedium,
//                                modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_small))
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//
////@Preview
////@Composable
////fun EachProfileScreenPreview() {
////    WoofTheme {
////        val dummyDogs = listOf(
////            Pooch(R.drawable.nox, R.string.dog_name_1, 2, R.string.dog_description_1),
////            Pooch(R.drawable.faye, R.string.dog_name_2, 4, R.string.dog_description_2)
////        )
////        EachProfileScreen(dogId = R.drawable.nox, dogList = dummyDogs)
////    }
////}
