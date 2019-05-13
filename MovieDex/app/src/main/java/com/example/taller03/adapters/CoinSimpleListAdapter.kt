package com.example.taller03.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taller03.MyCoinAdapter
import com.example.taller03.R
import com.example.taller03.pojos.Coin
import kotlinx.android.synthetic.main.list_item_coin.view.*

class CoinSimpleListAdapter(var coins:List<Coin>, val clickListener: (Coin) -> Unit): RecyclerView.Adapter<CoinSimpleListAdapter.ViewHolder>(), MyCoinAdapter{

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_coin, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = coins.size

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) =holder.bind(coins[pos], clickListener)

    override fun changeDataSet(newDataSet: List<Coin>) {
        this.coins = newDataSet
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(coin: Coin, clickListener: (Coin) -> Unit) = with(itemView){
            title_list_item.text = coin.name

            this.setOnClickListener { clickListener(coin) }
        }
    }
}