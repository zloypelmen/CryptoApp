package com.leonid.crupto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.leonid.crupto.adapters.CoinInfoAdapter
import com.leonid.crupto.pojo.CoinPriceInfo

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_price_list)

        val adapter = CoinInfoAdapter(this)

        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener{
            override fun onCoinClick(coinPriceInfo: CoinPriceInfo) {
                val intent = CoinDetailActivity.newIntent(
                    this@CoinPriceListActivity,
                    coinPriceInfo.fromSymbol
                )
                startActivity(intent)
            }
        }

        val rvCoinPriceList =  findViewById<RecyclerView>(R.id.rvCoinPriceList)
        rvCoinPriceList.adapter = adapter

        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]

        viewModel.priceList.observe(this) {
            adapter.coinInfoList = it
        }
    }
}