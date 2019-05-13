package com.example.taller03.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taller03.AppConstants
import com.example.taller03.MyCoinAdapter
import com.example.taller03.R
import com.example.taller03.adapters.CoinAdapter
import com.example.taller03.adapters.CoinSimpleListAdapter
import com.example.taller03.data.models.Coin
import kotlinx.android.synthetic.main.coins_list_fragment.*
import kotlinx.android.synthetic.main.coins_list_fragment.view.*

class MainListFragment: Fragment(){

    private lateinit var  coins :ArrayList<Coin>
    private lateinit var coinsAdapter : MyCoinAdapter
    var listenerTool :  SearchNewCoinListener? = null

    companion object {
        fun newInstance(dataset : ArrayList<Coin>): MainListFragment{
            val newFragment = MainListFragment()
            newFragment.coins = dataset
            return newFragment
        }
    }

    interface SearchNewCoinListener{
        fun searchMovie(coinName: String)

        fun managePortraitItemClick(coin: Coin)

        fun manageLandscapeItemClick(coin: Coin)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.coins_list_fragment, container, false)

        if(savedInstanceState != null) coins = savedInstanceState.getParcelableArrayList<Coin>(AppConstants.MAIN_LIST_KEY)!!

        initRecyclerView(resources.configuration.orientation, view)
        initSearchButton(view)

        return view
    }

    fun initRecyclerView(orientation:Int, container:View){
        val linearLayoutManager = LinearLayoutManager(this.context)

        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            coinsAdapter = CoinAdapter(coins, { coin: Coin ->listenerTool?.managePortraitItemClick(coin)})
            container.coin_list_rv.adapter = coinsAdapter as CoinAdapter
        }
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            coinsAdapter = CoinSimpleListAdapter(coins, { coin: Coin ->listenerTool?.manageLandscapeItemClick(coin)})
            container.coin_list_rv.adapter = coinsAdapter as CoinSimpleListAdapter
        }

        container.coin_list_rv.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
        }
    }

    fun initSearchButton(container:View) = container.add_coin_btn.setOnClickListener {
        listenerTool?.searchMovie(coin_name_et.text.toString())
    }

    fun updateMoviesAdapter(coinList: ArrayList<Coin>){ coinsAdapter.changeDataSet(coinList) }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is SearchNewCoinListener) {
            listenerTool = context
        } else {
            throw RuntimeException("Se necesita una implementación de  la interfaz")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(AppConstants.MAIN_LIST_KEY, coins)
        super.onSaveInstanceState(outState)
    }

    override fun onDetach() {
        super.onDetach()
        listenerTool = null
    }
}