package com.example.taller03.data

import android.provider.BaseColumns

object DatabaseContract {
    object CoinEntry : BaseColumns { // Se guardan los datos relevantes de la tabla, como su nombre y sus campos.

        const val TABLE_NAME = "moneda"
        const val COLUMN_NAME = "name"
        const val COLUMN_COUNTRY = "country"
        const val COLUMN_VALUE = "value"
        const val COLUMN_VALUE_US = "value_us"
        const val COLUMN_YEAR = "year"
        const val COLUMN_REVIEW = "review"
        const val COLUMN_ISAVAILABLE = "isavailable"
        const val COLUMN_IMG = "img"
    }
}