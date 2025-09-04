package com.example.woof.ui.navigation


//アプリのナビゲーション先を記述するインターフェース
interface NavigationDestination {

    //コンポーザブルのパスを定義する一意の名前
    val route: String

    //画面に表示されるタイトルを含む文字列リソース ID
    val titleRes: Int
}