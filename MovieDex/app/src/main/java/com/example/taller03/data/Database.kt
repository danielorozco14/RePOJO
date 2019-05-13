package com.example.taller03.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import com.example.taller03.adapters.CoinAdapter
import com.example.taller03.data.models.Coin

private const val SQL_CREATE_ENTRIES =
    "CREATE TABLE ${DatabaseContract.CoinEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${DatabaseContract.CoinEntry.COLUMN_NAME} TEXT," +
            "${DatabaseContract.CoinEntry.COLUMN_COUNTRY} TEXT," +
            "${DatabaseContract.CoinEntry.COLUMN_VALUE} TEXT)," +
            "${DatabaseContract.CoinEntry.COLUMN_VALUEUS} TEXT," +
            "${DatabaseContract.CoinEntry.COLUMN_YEAR} TEXT," +
            "${DatabaseContract.CoinEntry.COLUMN_REVIEW} TEXT," +
            "${DatabaseContract.CoinEntry.COLUMN_ISAVAILABLE} TEXT," +
            "${DatabaseContract.CoinEntry.COLUMN_IMG} TEXT," +
            "${DatabaseContract.CoinEntry.COLUMN_YEAR} TEXT,)"

private const val SQL_DELETE_ENTRIES =
    "DROP TABLE IF EXISTS ${DatabaseContract.CoinEntry.TABLE_NAME}"

class Database(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun insertCoin(
        adapter: CoinAdapter,
        name: String,
        country: String,
        value: String,
        valueUS: String,
        year: String,
        review: String,
        isAvailable: Boolean,
        img: String
    ) {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(DatabaseContract.CoinEntry.COLUMN_NAME, name)
            put(DatabaseContract.CoinEntry.COLUMN_COUNTRY, country)
            put(DatabaseContract.CoinEntry.COLUMN_VALUE, value)
            put(DatabaseContract.CoinEntry.COLUMN_VALUEUS, valueUS)
            put(DatabaseContract.CoinEntry.COLUMN_YEAR, year)
            put(DatabaseContract.CoinEntry.COLUMN_REVIEW, review)
            put(DatabaseContract.CoinEntry.COLUMN_ISAVAILABLE, isAvailable)
            put(DatabaseContract.CoinEntry.COLUMN_IMG, img)
        }

        val newRow = db?.insert(DatabaseContract.CoinEntry.TABLE_NAME, null, values)

        if (newRow == -1L) {
            Log.i("DB", "Moneda no insertada")
        } else {
            Log.i("DB", "Moneda insertada")
            adapter.changeDataSet(readCoins())
        }

    }

    fun readCoins(): List<Coin> {
        val db = this.readableDatabase

        val projection = arrayOf(
            BaseColumns._ID,
            DatabaseContract.CoinEntry.COLUMN_NAME,
            DatabaseContract.CoinEntry.COLUMN_COUNTRY,
            DatabaseContract.CoinEntry.COLUMN_VALUE,
            DatabaseContract.CoinEntry.COLUMN_VALUEUS,
            DatabaseContract.CoinEntry.COLUMN_YEAR,
            DatabaseContract.CoinEntry.COLUMN_REVIEW,
            DatabaseContract.CoinEntry.COLUMN_ISAVAILABLE,
            DatabaseContract.CoinEntry.COLUMN_IMG
        )

        val sortOrder = "${DatabaseContract.CoinEntry.COLUMN_NAME} ASC"

        val cursor = db.query(
            DatabaseContract.CoinEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            sortOrder
        )

        var lista = mutableListOf<Coin>()

        with(cursor) {
            while (moveToNext()) {
                var persona = Coin(
                    getInt(getColumnIndexOrThrow(BaseColumns._ID)),
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_NAME)),
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_COUNTRY)),
                    getInt(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_VALUE)),
                    getInt(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_VALUEUS)),
                    getInt(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_YEAR)),
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_REVIEW)),
                    (getInt(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_ISAVAILABLE))),
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_IMG))
                )
                lista.add(persona)
            }
        }
        return lista
    }

    companion object {
        const val DATABASE_NAME = "currency.db"
        const val DATABASE_VERSION = 1
    }
}