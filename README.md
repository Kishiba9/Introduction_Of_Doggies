(IODs)Introduction of Doggies App <br> 犬のプロフィールを登録・閲覧できるシンプルなアプリ
==================================

概要 (Overview)
------------
Introduction of Doggies (IODs) は、わんちゃんたちのプロフィールを観賞できるアプリです。<br>
This is an app where you can view profiles of dogs.

プロフィールには以下の情報を登録できます: <br>
You can register the following information:
- 名前(Name)
- 年齢(Age)
- 趣味(Hobby)
- フリーコメント(Free comment)

機能 (Features)
-------------
- ログイン/ログオン (ユーザごとのIDとパスワードは未実装)
- 犬のプロフィール登録・保存・編集・参照・削除
- アプリ起動時にサンプル犬データを読み込み (createFromAssets を使用)
- Jetpack Compose と Material3 を用いた UI
- Roomによるデータ永続化

--------
- Login / Logon (DB for user-specific ID and password is not implemented)
- Register, save, edit, view, and delete dog profiles
- Load sample dog profiles on app startup using createFromAssets
- UI built with Jetpack Compose and Material3
- Data persistence with Room

使用技術 (Tech Stack)
--------------
- Kotlin
- Jetpack Compose
- Material3
- State / Flow / StateFlow による UI 状態管理
- Coroutine (非同期処理)
- MVVM アーキテクチャによる UI とデータの分離
- Room (データ永続化)
- Jetpack Navigation (NavController / NavHost による画面遷移)

学習要素 (Key Concepts)
--------------
- State / StateFlow での状態管理
- Card / LazyColumn の使用
- Composable Functions
- Classes
- Lists
- Rows/Columns
- Modifiers
- Button click handlers

セットアップ (Getting Started)
---------------
1. リポジトリをクローンしてください。  
   Clone this repository.
2. Android Studio で開きます。  
   Open the project in Android Studio.
3. エミュレータまたは実機で実行します。  
   Run on an emulator or physical device.

今後の展望 (Future Work)
---------------
- プロフィール画像のアップロード機能
- UI デザインの改善
- API連携の実装テスト
- Firebaseとの連携
- 検索・ソート機能の追加

--------
- Add profile image upload feature
- Improve UI design
- Test implementation of API integration
- Integration with Firebase
- Add search and sort functionality  
