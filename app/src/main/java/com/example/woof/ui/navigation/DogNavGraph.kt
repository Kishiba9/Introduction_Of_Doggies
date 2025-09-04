package com.example.woof.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.woof.Login.LoginDestination
import com.example.woof.Login.LoginScreen
import com.example.woof.ui.dog.DogDetailDestination
import com.example.woof.ui.dog.DogDetailScreen
import com.example.woof.ui.dog.DogEditDestination
import com.example.woof.ui.dog.DogEditScreen
import com.example.woof.ui.dog.DogEntryDestination
import com.example.woof.ui.dog.DogEntryScreen
import com.example.woof.ui.home.HomeDestination
import com.example.woof.ui.home.HomeScreen

@Composable
fun DogNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = LoginDestination.route,
        modifier = modifier,
    ) {

        composable(route = LoginDestination.route) {
            LoginScreen(
                onClickToHomeScreen = { navController.navigate(HomeDestination.route) }
            )
        }

        //ホーム画面のルート
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToDogEntry = { navController.navigate(DogEntryDestination.route) },
                navigateToDogUpdate = {
                    navController.navigate( "${DogDetailDestination.route}/${it}" )
                },
                onLogout = { navController.navigate(LoginDestination.route) {
                    popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        //エントリー画面のルート
        composable(route = DogEntryDestination.route) {
            DogEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }

        //編集画面のルート
        composable( //以下のコードを後で調査
            route = DogEditDestination.routeWithArgs,
            arguments = listOf(navArgument(DogEditDestination.dogIdArg){
                type = NavType.IntType
            })
        ) {
            DogEditScreen(
                onNavigateUp = { navController.popBackStack() },
                onNavigateBack = { navController.navigateUp() }
            )
        }

        //詳細画面のルート
        composable(
            route = DogDetailDestination.routeWithArgs,
            arguments = listOf(navArgument(DogDetailDestination.dogIdArg){
                type = NavType.IntType
            })
        ) {
            DogDetailScreen(
                navigateToEditDog = { navController.navigate("${DogEditDestination.route}/$it") }, //"${DogEditDestination.route}/${it}"この書き方ではなく左のような書き方なのはなぜ(HomeScreenのnavigateとは違う) -> 基本的には$itで問題ない（変数の後に文字が続く場合に使用できる　例）val route = "dogDetail/${id}dogs"のような感じ）
                navigateBack = { navController.navigateUp() } //navigateUp()とpopBackStack()の違いは？ ->動きとしては基本同じ
            )
        }
    }
}