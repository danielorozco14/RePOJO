package com.example.taller03.activities

import android.content.Intent
import android.content.res.Configuration
import android.database.sqlite.SQLiteDatabase
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import com.example.taller03.data.DatabaseContract
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.taller03.AppConstants
import com.example.taller03.R
import com.example.taller03.data.Database
import com.example.taller03.fragments.MainContentFragment
import com.example.taller03.fragments.MainListFragment
import com.example.taller03.network.NetworkUtils
import com.example.taller03.data.models.Coin
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.viewer_coin.*
import org.json.JSONArray
import java.io.IOException
import java.net.URL

class MainActivity : AppCompatActivity(), MainListFragment.SearchNewCoinListener,
    NavigationView.OnNavigationItemSelectedListener {




    private lateinit var mainFragment: MainListFragment
    private lateinit var mainContentFragment: MainContentFragment


    private var coinList = ArrayList<Coin>()

    var dbHelper = Database(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        coinList = savedInstanceState?.getParcelableArrayList(AppConstants.dataset_saveinstance_key)
            ?: ArrayList<Coin>()

        val toggle=ActionBarDrawerToggle(
            this,drawer_layout,toolbarviewer,R.string.navigation_drawer_open,R.string.navigation_drawer_close
        )

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener (this)

        initMainFragment()
        FetchCoin().execute()

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.country_ElSalvador -> {


            }
            R.id.country_Panama -> {

            }
            R.id.country_CostaRica -> {

            }
            R.id.country_Honduras -> {

            }
            R.id.country_Belize -> {

            }
            R.id.country_Guatemala -> {

            }
        }


        drawer_layout.closeDrawer(GravityCompat.START)
        return true

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
        mainFragment.updateCoinsAdapter(coinList)
        Log.d("Number", coinList.size.toString())
    }

    override fun searchCoin(coinName: String) {
        FetchCoin().execute()
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

    private inner class FetchCoin : AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg params: Void?): String {
            var url: URL? = null
            url =
                NetworkUtils().buildSearchUrl() //Esta madre te construye el url que se meterá en la poke api
            try {
                return NetworkUtils().getResponseFromHttpUrl(url)//el json como una string
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return ""// un null XD

        }


        override fun onPostExecute(coinInfo: String) {
            super.onPostExecute(coinInfo)
            if (!coinInfo.isEmpty()) {
                var coinJson = JSONArray(coinInfo)
                for (num in 0 until coinJson.length()) {

                    val coin = Gson().fromJson<Coin>(coinJson.getString(num), Coin::class.java)
                    addCoinToList(coin)
                }

            } else {
                Toast.makeText(this@MainActivity, "Ha ocurrido un error", Toast.LENGTH_LONG).show()
            }
        }
    }
}

