//package com.example.woof.ui.home.old
//
//import androidx.annotation.StringRes
//import androidx.compose.animation.AnimatedContentTransitionScope
//import androidx.compose.animation.core.tween
//import androidx.compose.animation.scaleIn
//import androidx.compose.animation.scaleOut
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.automirrored.filled.ExitToApp
//import androidx.compose.material3.CenterAlignedTopAppBar
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.dimensionResource
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import androidx.navigation.NavType
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.currentBackStackEntryAsState
//import androidx.navigation.compose.rememberNavController
//import androidx.navigation.navArgument
//import com.example.woof.DogApplication
//import com.example.woof.Login.LoginScreen
//import com.example.woof.R
//import com.example.woof.ui.dog.old.EachProfileScreen
//import com.example.woof.ui.dog.old.DogListScreen
//import com.example.woof.ui.dog.WoofViewModel
//
//
//enum class WoofAppScreen(@StringRes val title: Int) {
//    Login(title = R.string.login_title),
//    WoofScreen(title = R.string.app_name),
//    WoofDetail(title = R.string.dog_detail)
//}
//
//@Composable
//fun WoofApp(
//    viewModel: WoofViewModel = viewModel(),
//    navController: NavHostController = rememberNavController()
//) {
//
//    // Get current back stack entry
//    val backStackEntry by navController.currentBackStackEntryAsState()
//     // 現在の場所と、そこに至るまでの履歴が入ってる
//
//    // Get the name of the current screen
//    // アプリケーションバーで使う変数
//    val currentScreen = WoofAppScreen.valueOf(
//        //ルートから引数部分を除外する
//        backStackEntry?.destination?.route?.substringBefore('/') ?: WoofAppScreen.Login.name
//        //backStackEntry?.destination?.route ?: WoofAppScreen.Login.name
//    )
//
//    Scaffold(
//        topBar = {
//            if (currentScreen == WoofAppScreen.Login){
//                LoginAppBar()
//            } else {
//                WoofTopAppBar(
//                    canNavigateBack = currentScreen == WoofAppScreen.WoofDetail,
//                    navigateUp = {navController.navigateUp()},
//                    onLogoutClick = {
//                        navController.navigate(WoofAppScreen.Login.name) {
//                            //バックスタック上の全ての画面をポップし、新しいLoginScreenを唯一の画面にする
//                            popUpTo(navController.graph.id) { // nav graphのルートIDを指定:ナビゲーショングラフ全体のルートデスティネーションのIDを指します。
//                                inclusive = true // ルートIDのデスティネーション自体も含む
//                            }
//                            // ログイン画面を再起動しないようにする (通常は必要ないが、意図しない再起動を防ぐため)
//                            launchSingleTop = true
//                        }
//                    }
//                )
//            }
//        }
//    ) { paddingValues ->
//
//        // StateFlowをComposeのStateとして購読し、値が更新されるとComposableが再コンポーズされる
//        val uiState by viewModel.uiState.collectAsState()
//
//        NavHost(
//            navController = navController,
//            startDestination = WoofAppScreen.Login.name,
//            modifier = Modifier.fillMaxSize()
//        ) {
//            composable(
//                route = WoofAppScreen.Login.name,
//                exitTransition = {
//                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up, animationSpec = tween(700)) +
//                            scaleOut(animationSpec = tween(700))
//                    //Jetpack Compose Navigationのバージョン2.7.0以降では、アニメーション関連のスコープがAnimatedContentScopeからAnimatedContentTransitionScopeに変更されました。
//                }
//            ) {
//                LoginScreen(
//                    onClickToMainScreen = { navController.navigate(WoofAppScreen.WoofScreen.name) },
//                    modifier = Modifier.padding(paddingValues)
//                )
//            }
//
//            composable(
//                route = WoofAppScreen.WoofScreen.name,
//                enterTransition = {
//                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up, animationSpec = tween(700)) +
//                            scaleIn(animationSpec = tween(700))
//                }
//            ) {
//
//                val dogListViewModel: WoofViewModel = viewModel(
//                    factory = (LocalContext.current.applicationContext as DogApplication).container.dogRepository as ViewModelProvider.Factory?
//                )
//
//                // DogListScreen で使用する viewModel をここで取得します。
//                // WoofApp に渡された viewModel インスタンスをそのまま渡してもよいですし、
//                // あるいは DogListScreen の定義と同じ factory を使って新たに取得しても構いません。
//                // DogListScreen の定義に合わせるのが推奨です。
//                DogListScreen(
//                    navigateToDogEntry = {}, //犬の新規登録画面へのナビゲーションを実装
//                    navigateToDogUpdate = {}, //犬の更新画面へのナビゲーションを実装 (例: navController.navigate("dogUpdateScreen/$id")
//                    onClickToDetail = { id -> navController.navigate("${WoofAppScreen.WoofDetail.name}/$id") },
//                    viewModel = dogListViewModel
//                )
//
//                //WoofScreen(
//                //    poochesList = uiState.dogDetails,
//                //    onClickToDetail = { dogId -> navController.navigate("${WoofAppScreen.WoofDetail.name}/$dogId") },
//                //    modifier = Modifier.padding(paddingValues) //ScaffoldのpaddingValuesを適用してからfillMaxSize
//                //)
//            }
//
//            composable(
//                route = "${WoofAppScreen.WoofDetail.name}/{id}",
//                arguments = listOf(navArgument("id") { type = NavType.IntType }),
//                enterTransition = {
//                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down, animationSpec = tween(700)) +
//                            scaleIn(animationSpec = tween(700))
//                }
//            ) { backStackEntry ->
//                // backStackEntryからdogIdを取得
//                val dogId = backStackEntry.arguments?.getInt("id")
//                if(dogId != null) {
//                    EachProfileScreen(
//                        dogId = dogId, // 取得したdogIdを渡す
//                        //dogList = viewModel.allDogs,
//                        modifier = Modifier.padding(paddingValues)
//                    )
//                } else {
//                    // エラーハンドリング（dogIdが見つからない場合）
//                    Text(
//                        text = "Error: Dog ID is missing",
//                        modifier = Modifier.padding(paddingValues)
//                    )
//                }
//            }
//        }
//    }
//}
//
////@Composable
////fun WoofScreen(
////    poochesList: DogDetails,
////    onClickToDetail: (Int) -> Unit,
////    modifier: Modifier = Modifier) {
////    LazyColumn(
////        modifier = modifier,
////        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
////        contentPadding = PaddingValues(dimensionResource(R.dimen.padding_small))
////    ){
////        items(poochesList){ dog ->
////            DogItem(
////                dog = dog,
////                onClickToDetail = onClickToDetail
////            )
////        }
////    }
////}
//
////@Composable
////fun DogItem(
////    dog: DogDetails,
////    onClickToDetail: (Int) -> Unit,
////    modifier: Modifier = Modifier) {
////    var expanded by rememberSaveable {mutableStateOf(false)}
////    val color by animateColorAsState(
////        targetValue = if (expanded) MaterialTheme.colorScheme.tertiaryContainer
////        else MaterialTheme.colorScheme.primaryContainer,
////    )
////    Card(
////        modifier = modifier
////            .clickable{ onClickToDetail(dog.imageResourceId) }
////    ){
////        Column(
////            modifier = Modifier
////                .fillMaxWidth()
////                .background(color = color) //Cardで直接色をつけることが推奨されている
////                .padding(dimensionResource(R.dimen.padding_small))
////                .animateContentSize(
////                    animationSpec = spring(
////                        dampingRatio = Spring.DampingRatioMediumBouncy,
////                        stiffness = Spring.StiffnessLow
////                    )
////                )
////        ){
////            Row(
////                verticalAlignment = Alignment.CenterVertically
////            ){
////                DogIcon(dog = dog)
////                DogInformation(
////                    dog = dog,
////                    modifier = Modifier
////                        .weight(1f)
////                        .padding(start = dimensionResource(R.dimen.padding_medium))
////                )
////                //Spacer(modifier = Modifier.weight(1f))でもよい
////                WoofIconButton(
////                    expanded = expanded,
////                    onClick = {expanded = !expanded}
////                )
////            }
////            if(expanded){
////                DogHobby(
////                    dogHobby = dog.hobbies,
////                    modifier = Modifier.padding(
////                        start = dimensionResource(R.dimen.padding_medium),
////                        end = dimensionResource(R.dimen.padding_medium),
////                        top = dimensionResource(R.dimen.padding_small),
////                        bottom = dimensionResource(R.dimen.padding_medium)
////                    )
////                )
////            }
////        }
////    }
////}
////
////@Composable
////fun DogHobby(
////    @StringRes dogHobby: Int,
////    modifier: Modifier = Modifier
////) {
////    Column(modifier = modifier)
////    {
////        Text(
////            text = stringResource(R.string.about),
////            style = MaterialTheme.typography.bodyLarge
////        )
////        Text(
////            text = stringResource(dogHobby),
////            style = MaterialTheme.typography.bodyLarge
////        )
////    }
////}
////
////@Composable
////fun DogIcon(
////    dog: Pooch,
////    modifier: Modifier = Modifier
////) {
////    Image(
////        painter = painterResource(id = dog.imageResourceId),
////        contentDescription = null,
////        modifier = modifier
////            .size(dimensionResource(R.dimen.image_size))
////            .clip(MaterialTheme.shapes.small)
////            .aspectRatio(1f),
////        contentScale = ContentScale.Crop
////    )
////}
////
////@Composable
////fun DogInformation(
////    dog: Pooch,
////    modifier: Modifier = Modifier
////) {
////    Column(
////        modifier = modifier
////    ){
////        Text(
////            text = stringResource(id = dog.name),
////            style = MaterialTheme.typography.displayMedium,
////            fontWeight = FontWeight.Bold,
////            modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_small))
////        )
////        Text(
////            text = stringResource(R.string.years_old, dog.age),
////            style = MaterialTheme.typography.bodyLarge,
////            modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_small))
////        )
////    }
////}
////
////@Composable
////fun WoofIconButton(
////    expanded: Boolean,
////    onClick: () -> Unit,
////    modifier: Modifier = Modifier
////) {
////    IconButton(
////        onClick = onClick,
////        modifier = modifier
////    ) {
////        Icon(
////            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
////            contentDescription = stringResource(R.string.expand_button_content_description),
////            tint = MaterialTheme.colorScheme.secondary
////        )
////    }
////}
////
//@Composable
//fun WoofTopAppBar(
//    canNavigateBack: Boolean,
//    navigateUp: () -> Unit,
//    onLogoutClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    CenterAlignedTopAppBar(
//        title = {
//            Row(
//                verticalAlignment = Alignment.CenterVertically
//            )
//            {
//                Image(
//                    painter = painterResource(R.drawable.ic_woof_logo),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .size(dimensionResource(R.dimen.image_size))
//                        .padding(dimensionResource(R.dimen.padding_small))
//                )
//                Text(
//                    text = stringResource(R.string.app_name),
//                    style = MaterialTheme.typography.displayLarge,
//                    modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_small))
//                )
//            }
//        },
//        modifier = modifier.padding(dimensionResource(R.dimen.padding_medium)),
//        navigationIcon = {
//            if (canNavigateBack) {
//                IconButton(onClick = navigateUp) {
//                    Icon(
//                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                        contentDescription = stringResource(R.string.back_button_description)
//                    )
//                }
//            }
//        },
//        actions = {
//            IconButton(onClick = onLogoutClick) {
//                Icon(
//                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
//                    contentDescription = null
//                )
//            }
//        }
//    )
//}
//
//@Composable
//fun LoginAppBar(modifier: Modifier = Modifier){
//    CenterAlignedTopAppBar(
//        title = {
//            Row (
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//
//                Image(
//                    painter = painterResource(R.drawable.ic_woof_logo),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .size(dimensionResource(R.dimen.image_size))
//                        .padding(dimensionResource(R.dimen.padding_small))
//                )
//
//                Text(
//                    text = stringResource(R.string.login_title),
//                    style = MaterialTheme.typography.displayLarge,
//                    modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_small))
//                )
//            }
//        },
//        modifier = modifier.padding(dimensionResource(R.dimen.padding_medium))
//    )
//}
//
////@Preview
////@Composable
////fun WoofScreenPreview() {
////    WoofTheme {
////        val dummyPooches = listOf(
////            Pooch(R.drawable.nox, R.string.dog_name_1, 2, R.string.dog_description_1),
////            Pooch(R.drawable.faye, R.string.dog_name_2, 4, R.string.dog_description_2)
////        )
////        WoofScreen(
////            poochesList = dummyPooches,
////            onClickToDetail = {}
////        ) //uiStateで外部アクセスへの対応をしたのにdogsにアクセスできてしまうのはまずいのでは？ ⇒その通り、プレビューがViewModelの役割を無視している
////    }
////}