package com.example.iods

import android.app.Application
import com.example.iods.data.AppContainer
import com.example.iods.data.AppDataContainer

class DogApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
    //onCreate内で変数宣言しないのは、ローカル変数として扱われてしまうため
    //この場合、onCreateメソッドの実行が終わると、containerは消滅してしまう
    //DogApplication クラスの目的は、container インスタンスをアプリケーション全体で共有し、どこからでもアクセスできるようにする
}