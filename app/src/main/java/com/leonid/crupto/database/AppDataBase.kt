package com.leonid.crupto.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.leonid.crupto.pojo.CoinPriceInfo

@Database(entities = [CoinPriceInfo::class], version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {
    companion object{
        fun getInstance(context: Context): AppDataBase{
            synchronized(LOCK){
                db?.let {return it}
                val instance: AppDataBase = Room.databaseBuilder(
                    context,
                    AppDataBase::class.java,
                    DB_NAME
                ).build()
                db = instance
                return instance
            }
        }

        private var db: AppDataBase? = null
        private val DB_NAME = "main.db"
        private val LOCK = Any()
    }

    abstract fun coinPriceInfoDao(): CoinPriceInfoDao
}