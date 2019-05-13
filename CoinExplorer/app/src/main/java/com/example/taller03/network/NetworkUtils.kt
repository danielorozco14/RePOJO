package com.example.taller03.network

import android.net.Uri
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*

class NetworkUtils {

    val COINS_API_BASEURL = "http://pdm-taller2.herokuapp.com/coins"
    val TOKEN_API = "coins"

    fun buildSearchUrl(): URL {
        val builtUri = Uri.parse(COINS_API_BASEURL)
            .buildUpon()
            .build()

        return try {
            URL(builtUri.toString())
        } catch (e: MalformedURLException) {
            URL("")
        }
    }

    @Throws(IOException::class)
    fun getResponseFromHttpUrl(url: URL): String {
        val urlConnection = url.openConnection() as HttpURLConnection
        try {
            val `in` = urlConnection.inputStream

            val scanner = Scanner(`in`)
            scanner.useDelimiter("\\A")

            val hasInput = scanner.hasNext()
            return if (hasInput) {
                scanner.next()
            } else {
                ""
            }
        } finally {
            urlConnection.disconnect()
        }
    }

}