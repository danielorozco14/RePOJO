package com.example.taller03

import com.example.taller03.data.models.Coin

object AppConstants{
    val dataset_saveinstance_key = "CLE"
    val MAIN_LIST_KEY = "key_list_coins"
}

interface MyCoinAdapter {
    fun changeDataSet(newDataSet : List<Coin>)
}