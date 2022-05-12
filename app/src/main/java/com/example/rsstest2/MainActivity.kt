package com.example.rsstest2


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rsstest2.model.Article
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.rsstest2.rss.Downloader


const val TAG = "mydebug"

class MainActivity : AppCompatActivity() {


    val urlAddress = "https://smaforetagarna.se/nyheter/feed/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
      //  setSupportActionBar(toolbar)
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton

        val rv = findViewById<View>(R.id.rv) as RecyclerView
        rv.adapter = MyAdapter(ArrayList<Article>())
        rv.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener {
                Log.d(TAG, "trycker på knappen")
                Downloader(this@MainActivity, urlAddress, rv).execute()

        }
    }
}

