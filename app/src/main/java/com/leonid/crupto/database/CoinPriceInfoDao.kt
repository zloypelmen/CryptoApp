package com.leonid.crupto.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.leonid.crupto.pojo.CoinPriceInfo
import androidx.room.Query

@Dao
interface CoinPriceInfoDao {
    @Query("SELECT * FROM full_price_list ORDER BY lastUpdate DESC")
    fun getPriceList(): LiveData<List<CoinPriceInfo>>

    @Query("SELECT * FROM full_price_list WHERE fromSymbol == :fSym LIMIT 1")
    fun getPriceCoinInfoAboutCoin(fSym: String): LiveData<CoinPriceInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    fun insertPriseList(priceList: List<CoinPriceInfo>)
}