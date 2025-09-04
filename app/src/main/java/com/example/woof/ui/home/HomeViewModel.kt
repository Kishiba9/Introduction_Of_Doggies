package com.example.woof.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.woof.data.Dog
import com.example.woof.data.DogRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(dogRepository: DogRepository) : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> =
        dogRepository.getAllDogStream().map { HomeUiState(it) } //犬のリストをストリーム（Flow）として非同期で取得します。このストリームは、データベースに犬の情報が追加・削除されたときに、自動的に最新のリストを流してくれます。
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )
    //stateIn : Flow を StateFlow に変換するオペレータです。このコードでは、getAllDogStream() から流れてくる犬のリストを StateFlow に変換し、homeUiState に代入しています。
    //scope = viewModelScope: ViewModel が破棄されるまで（画面が閉じられるまで）ストリームを監視し続けます。
    //started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS): UIがこのデータを購読している間だけ、データのストリームをアクティブにします。UIが表示されていないときは無駄な処理を行わないようにしています。

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
    //companion objectの内部に定義された変数や関数は、そのクラスのインスタンス（オブジェクト）がいくつ生成されようとも、すべてのインスタンスで共有されます。
}

//HomeScreen用のUiState
data class HomeUiState(val dogList: List<Dog> = listOf())