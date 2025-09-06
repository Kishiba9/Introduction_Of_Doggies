package com.example.iods.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Dog::class], version = 1, exportSchema = false)
abstract class DogDatabase: RoomDatabase() {
    //DogDatabaseは抽象クラスなので、コンストラクタを呼び出せない。（DogDatabase()のように）

    abstract fun dogDao(): DogDao

    companion object {
        @Volatile
        private var Instance: DogDatabase? = null
        //@Volatileアノテーションは、マルチスレッド環境において、この変数の読み書きがメモリ上で同期されることを保証する。
        // これにより、複数のスレッドからアクセスがあった場合でも、常に最新の値が参照されるようになる

        /**
         * アプリケーション全体の DogDatabase のシングルトンインスタンスを提供します。
         * データベースは assets フォルダの "dogs.db" からプリポピュレーションされます。
         * @param context アプリケーションのコンテキスト
         * @return DogDatabase のシングルトンインスタンス
         */
        fun getDatabase(context: Context): DogDatabase {
            return Instance ?: synchronized(this) {
                //synchronized(this)ブロックは、一度に一つのスレッドしかそのブロック内のコードを実行できないようにロックをかける
                Instance ?: Room.databaseBuilder(context, DogDatabase::class.java, "dog_database")
                    // assetsフォルダからプリポピュレーションする設定
                    // "dogs.db" は assets フォルダ内に配置するSQLiteデータベースファイルの名前
                    .createFromAsset("dogs.db")
                    //.fallbackToDestructiveMigration(true)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}