package com.example.iods.data

import android.content.Context


interface AppContainer {
    val dogRepository: DogRepository
}

class AppDataContainer(private val context: Context): AppContainer {
    override val dogRepository: DogRepository by lazy {
        OfflineDogsRepository(DogDatabase.getDatabase(context).dogDao())
    }
    //AppDataContainerのインスタンスが作成された時点では、dogRepositoryはまだ初期化されません。
    //dogRepositoryプロパティが初めてアクセスされた時に、{ OfflineDogsRepository(DogDatabase.getDatabase(context).dogDao()) }ブロック内のコードが実行され、dogRepositoryが初期化されます。
    //一度初期化されると、その後のアクセスでは同じインスタンスが返されます。
    //何がいいか？
    //無駄な初期化の回避: dogRepositoryが実際に必要になるまで、データベースのインスタンス取得やDAOの生成といった比較的コストのかかる処理が行われません。もしアプリケーションの起動時にdogRepositoryが必ずしも必要でない場合、この遅延初期化はリソースの消費を抑え、起動時間の短縮に貢献します。
    //不必要なエラーの回避: contextが有効でないタイミングでDogAppContainerが初期化されても、すぐにDogDatabase.getDatabase(context)が呼び出されるわけではないため、潜在的なエラーを避けることができます。
    //by lazyはスレッドセーフなので同時アクセスはできない仕様になっている
}