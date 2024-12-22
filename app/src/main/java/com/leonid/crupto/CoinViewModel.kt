package com.leonid.crupto

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.leonid.crupto.api.ApiFactory
import com.leonid.crupto.database.AppDataBase
import com.leonid.crupto.pojo.CoinPriceInfo
import com.leonid.crupto.pojo.CoinPriceInfoRawData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()
    private val db = AppDataBase.getInstance(application)

    val priceList = db.coinPriceInfoDao().getPriceList()

    init {
        loadData()
    }

    fun getDetailInfo(fSym: String): LiveData<CoinPriceInfo>{
        return db.coinPriceInfoDao().getPriceCoinInfoAboutCoin(fSym)
    }

    private fun loadData() {
        val disposable = ApiFactory.apiService.getTopCoinsInfo(limit = 50)
            .map { it.data?.map { it.coinInfo?.name }?.joinToString(separator = ",").toString() }
            .flatMap { ApiFactory.apiService.getFullPriceList(fSyms = it) }
            .map { getPriceListFromRawData(it) }
            .delaySubscription(10000,TimeUnit.MILLISECONDS)
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io())
            .subscribe({
                db.coinPriceInfoDao().insertPriseList(it)
                Log.d("TEST_OF_LOADING_DATA", "Success$it")
            }, {
                Log.d("TEST_OF_LOADING_DATA", "Failed${it.message.toString()}")
            })
        compositeDisposable.add(disposable)
    }

    private fun getPriceListFromRawData(
        coinPriceInfoRawData: CoinPriceInfoRawData
    ): List<CoinPriceInfo> {

        val result = ArrayList<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject ?: return result

        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinPriceInfo::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}