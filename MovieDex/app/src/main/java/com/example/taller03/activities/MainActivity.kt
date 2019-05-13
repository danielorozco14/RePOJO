package com.example.taller03.activities

import android.content.Intent
import android.content.res.Configuration
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.taller03.AppConstants
import com.example.taller03.R
import com.example.taller03.data.Database
import com.example.taller03.fragments.MainContentFragment
import com.example.taller03.fragments.MainListFragment
import com.example.taller03.network.NetworkUtils
import com.example.taller03.data.models.Coin
import com.google.gson.Gson
import org.json.JSONArray
import java.io.IOException

class MainActivity() : AppCompatActivity(), MainListFragment.SearchNewCoinListener {
    private lateinit var mainFragment: MainListFragment
    private lateinit var mainContentFragment: MainContentFragment

    private var coinList = ArrayList<Coin>()

    var dbHelper = Database(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        coinList = savedInstanceState?.getParcelableArrayList(AppConstants.dataset_saveinstance_key)
            ?: ArrayList<Coin>()

        initMainFragment()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(AppConstants.dataset_saveinstance_key, coinList)
        super.onSaveInstanceState(outState)
    }

    fun initMainFragment() {
        mainFragment = MainListFragment.newInstance(coinList)

        val resource =
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                R.id.main_fragment
            else {
                mainContentFragment = MainContentFragment.newInstance(Coin())
                changeFragment(R.id.land_main_cont_fragment, mainContentFragment)

                R.id.land_main_fragment
            }

        changeFragment(resource, mainFragment)
    }

    fun addCoinToList(coin: Coin) {
        coinList.add(coin)
        mainFragment.updateMoviesAdapter(coinList)
        Log.d("Number", coinList.size.toString())
    }

    override fun searchCoin(coinName: String) {
        FetchCoin().execute(coinName)
    }

    override fun managePortraitItemClick(coin: Coin) {
        val coinBundle = Bundle()
        coinBundle.putParcelable("COIN", coin)
        startActivity(Intent(this, CoinViewerActivity::class.java).putExtras(coinBundle))
    }

    private fun changeFragment(id: Int, frag: Fragment) {
        supportFragmentManager.beginTransaction().replace(id, frag).commit()
    }

    override fun manageLandscapeItemClick(coin: Coin) {
        mainContentFragment = MainContentFragment.newInstance(coin)
        changeFragment(R.id.land_main_cont_fragment, mainContentFragment)
    }

    private inner class FetchCoin : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String): String {

            if (params.isNullOrEmpty()) return ""

            val coinName = params[0]
            val coinUrl = NetworkUtils().buildtSearchUrl(coinName)

            return try {
                NetworkUtils().getResponseFromHttpUrl(coinUrl)
            } catch (e: IOException) {
                ""
            }
        }

        override fun onPostExecute(coinInfo: String) {
            super.onPostExecute(coinInfo)
            if (!coinInfo.isEmpty()) {
                var coinJson = JSONArray(coinInfo)
                for (num in 0 until coinJson.length()) {

                    Log.d("PRUEBA0", coinJson.getString(num)) // sí se ejecuta

                    val coin = Gson().fromJson<Coin>(coinJson.getString(num), Coin::class.java)
                    Log.d("PRUEBA1", coinJson.getString(num))
                    addCoinToList(coin)
                }
//                if (!false/**coinJson.getString("Response") == "True"*/) {
//                    val coin = Gson().fromJson<Coin>(coinInfo, Coin::class.java)
//                    addCoinToList(coin)
//                } else {
//                    Toast.makeText(this@MainActivity, "No existe en la base de datos,", Toast.LENGTH_LONG).show()
//                }
            } else {
                Log.d("PRUEBA-1","ENTRÓ")
                Toast.makeText(this@MainActivity, "Ha ocurrido un error,", Toast.LENGTH_LONG).show()
            }
        }
    }
}

