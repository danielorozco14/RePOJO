package com.example.taller03.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.taller03.R
import com.example.taller03.data.models.Coin
import kotlinx.android.synthetic.main.viewer_coin.*

class CoinViewerActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewer_coin)

        setSupportActionBar(toolbarviewer)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //supportActionBar?.setDisplayShowHomeEnabled(true)
        collapsingtoolbarviewer.setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
        collapsingtoolbarviewer.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)

        val receiver: Coin = intent?.extras?.getParcelable("COIN") ?: Coin()
        init(receiver)
    }

    fun init(coin: Coin){
        Glide.with(this)
                .load(coin.img)
                .placeholder(R.drawable.ic_launcher_background)
                .into(app_bar_image_viewer)
        collapsingtoolbarviewer.title = coin.name
        app_bar_rating_viewer.text = coin.country

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {onBackPressed();true}
            else -> super.onOptionsItemSelected(item)
        }
    }
}