//package com.example.woof.ui.dog.old
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.Delete
//import androidx.compose.material.icons.filled.Edit
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.viewmodel.compose.viewModel
//import coil.compose.AsyncImage
//import coil.request.ImageRequest
//import com.example.woof.DogApplication
//import com.example.woof.R
//import com.example.woof.data.Dog
//import com.example.woof.ui.theme.WoofTheme
//
//// DogListScreen: 犬のリストを表示するComposable
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DogListScreen(
//    navigateToDogEntry: () -> Unit,
//    navigateToDogUpdate: (Int) -> Unit,
//    onClickToDetail: (Int) -> Unit,
//    viewModel: WoofViewModel = viewModel(
//        factory =
//            (LocalContext.current.applicationContext as DogApplication).container.dogRepository as ViewModelProvider.Factory?,
//    )
//) {
//    // WoofViewModelから直接allDogsのFlowを収集する
//    val allDogs by viewModel.allDogs.collectAsState(initial = emptyList())
//
//    Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(title = { Text("Woof! 犬たち") })
//        },
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = navigateToDogEntry,
//                modifier = Modifier.navigationBarsPadding() // ナビゲーションバーとの重なりを避ける
//            ) {
//                Icon(Icons.Default.Add, contentDescription = "犬を追加")
//            }
//        }
//    ) { paddingValues ->
//        if (allDogs.isEmpty()) { // allDogsを使用
//            Text(
//                text = "まだ犬がいません。新しい犬を追加しましょう！",
//                modifier = Modifier
//                    .fillMaxSize()
//                    .wrapContentSize(Alignment.Center)
//                    .padding(paddingValues)
//            )
//        }
//        else {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(paddingValues),
//                contentPadding = PaddingValues(16.dp),
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                items(items = allDogs, key = { it.id }) { dog -> // allDogsを使用
//                    DogListItem(
//                        dog = dog,
//                        onClickToDetail = onClickToDetail,
//                        onEditClick = { navigateToDogUpdate(dog.id) },
//                        onDeleteClick = { viewModel.deleteDog(dog) }
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun DogListItem(
//    dog: Dog,
//    onClickToDetail: (Int) -> Unit,
//    onEditClick: () -> Unit,
//    onDeleteClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Card(
//        modifier = modifier
//            .fillMaxWidth()
//            .clickable { onClickToDetail(dog.id) }, // カード全体をクリック可能にする
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            // 犬の画像
//            DogImage(dog.imageUri)
//
//            Spacer(Modifier.width(16.dp))
//
//            // 犬の情報
//            Column(modifier = Modifier.weight(1f)) {
//                Text(text = dog.name, style = MaterialTheme.typography.headlineSmall)
//                Text(text = "年齢: ${dog.age}歳", style = MaterialTheme.typography.bodyMedium)
//                Text(text = "趣味: ${dog.hobby}", style = MaterialTheme.typography.bodyMedium)
//                dog.freeComment?.let {
//                    if (it.isNotBlank()) {
//                        Text(text = "コメント: $it", style = MaterialTheme.typography.bodySmall)
//                    }
//                }
//            }
//
//            // アクションボタン
//            Row {
//                IconButton(onClick = onEditClick) {
//                    Icon(Icons.Default.Edit, contentDescription = "編集")
//                }
//                IconButton(onClick = onDeleteClick) {
//                    Icon(Icons.Default.Delete, contentDescription = "削除")
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun DogImage(imageUriString: String?, modifier: Modifier = Modifier) {
//    val context = LocalContext.current
//    AsyncImage(
//        model = ImageRequest.Builder(context)
//            .data(imageUriString)
//            .crossfade(true)
//            .build(),
//        contentDescription = "画像", // アクセシビリティのため適切なdescriptionを設定
//        contentScale = ContentScale.Crop,
//        error = painterResource(R.drawable.nox), // エラー時のプレースホルダー画像
//        placeholder = painterResource(R.drawable.nox), // ロード中のプレースホルダー画像
//        modifier = modifier
//            .size(80.dp)
//            .clip(CircleShape)
//        )
//}
//
//@Preview
//@Composable
//fun DogListItemPreview() {
//    WoofTheme {
//        val dog = Dog(id = 1, name = "Poch", age = 2, hobby = "Running", freeComment = "nice to meet you!", imageUri = "android.resource://com.example.woof/drawable/leroy")
//        DogListItem(
//            dog = dog,
//            {},
//            onDeleteClick = {},
//            onEditClick = {}
//        )
//    }
//}