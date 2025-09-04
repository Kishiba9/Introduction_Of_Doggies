//package com.example.woof.ui.navigation.old
//
//import androidx.compose.animation.AnimatedContentTransitionScope
//import androidx.compose.animation.core.tween
//import androidx.compose.animation.scaleIn
//import androidx.compose.animation.scaleOut
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import androidx.navigation.NavType
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.navArgument
//import com.example.woof.DogApplication
//import com.example.woof.Login.LoginScreen
//import com.example.woof.ui.dog.old.DogListScreen
//import com.example.woof.ui.dog.old.EachProfileScreen
//import com.example.woof.ui.dog.WoofViewModel
//import com.example.woof.ui.home.old.WoofAppScreen
//
//@Composable
//fun DogNavHost(
//    navController: NavController,
//    modifier: Modifier = Modifier
//) {
//    NavHost(
//        navController = navController,
//        startDestination = LoginDestination.route,
//        modifier = modifier
//    ) {
//        composable(
//            route = LoginDestinatiom.route,
//            exitTransition = {
//                slideOutOfContainer(
//                    AnimatedContentTransitionScope.SlideDirection.Up,
//                    animationSpec = tween(700)
//                ) +
//                        scaleOut(animationSpec = tween(700))
//                //Jetpack Compose Navigationのバージョン2.7.0以降では、アニメーション関連のスコープがAnimatedContentScopeからAnimatedContentTransitionScopeに変更されました。
//            }
//        ) {
//            LoginScreen(
//                onClickToMainScreen = { navController.navigate() },
//                modifier = padding(paddingValues)
//            )
//        }
//
//        composable(
//            route = WoofAppScreen.WoofScreen.name,
//            enterTransition = {
//                slideIntoContainer(
//                    AnimatedContentTransitionScope.SlideDirection.Up,
//                    animationSpec = tween(700)
//                ) +
//                        scaleIn(animationSpec = tween(700))
//            }
//        ) {
//
//            val dogListViewModel: WoofViewModel = viewModel(
//                factory = (LocalContext.current.applicationContext as DogApplication).container.dogRepository as ViewModelProvider.Factory?
//            )
//
//            // DogListScreen で使用する viewModel をここで取得します。
//            // WoofApp に渡された viewModel インスタンスをそのまま渡してもよいですし、
//            // あるいは DogListScreen の定義と同じ factory を使って新たに取得しても構いません。
//            // DogListScreen の定義に合わせるのが推奨です。
//            DogListScreen(
//                navigateToDogEntry = {}, //犬の新規登録画面へのナビゲーションを実装
//                navigateToDogUpdate = {}, //犬の更新画面へのナビゲーションを実装 (例: navController.navigate("dogUpdateScreen/$id")
//                onClickToDetail = { id -> navController.navigate("${WoofAppScreen.WoofDetail.name}/$id") },
//                viewModel = dogListViewModel
//            )
//
//            //WoofScreen(
//            //    poochesList = uiState.dogDetails,
//            //    onClickToDetail = { dogId -> navController.navigate("${WoofAppScreen.WoofDetail.name}/$dogId") },
//            //    modifier = Modifier.padding(paddingValues) //ScaffoldのpaddingValuesを適用してからfillMaxSize
//            //)
//        }
//
//        composable(
//            route = "${WoofAppScreen.WoofDetail.name}/{id}",
//            arguments = listOf(navArgument("id") { type = NavType.IntType }),
//            enterTransition = {
//                slideIntoContainer(
//                    AnimatedContentTransitionScope.SlideDirection.Down,
//                    animationSpec = tween(700)
//                ) +
//                        scaleIn(animationSpec = tween(700))
//            }
//        ) { backStackEntry ->
//            // backStackEntryからdogIdを取得
//            val dogId = backStackEntry.arguments?.getInt("id")
//            if (dogId != null) {
//                EachProfileScreen(
//                    dogId = dogId, // 取得したdogIdを渡す
//                    //dogList = viewModel.allDogs,
//                    modifier = padding(paddingValues)
//                )
//            } else {
//                // エラーハンドリング（dogIdが見つからない場合）
//                Text(
//                    text = "Error: Dog ID is missing",
//                    modifier = padding(paddingValues)
//                )
//            }
//        }
//    }
//}