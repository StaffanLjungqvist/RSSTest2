package com.example.rsstest2


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rsstest2.model.Article
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.rsstest2.rss.Downloader
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


const val TAG = "mydebug"

class MainActivity : AppCompatActivity() {

    lateinit var model: MainViewModel
    lateinit var adapter: MyAdapter


    val urlAddress = "https://smaforetagarna.se/nyheter/feed/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = ViewModelProvider(this)[MainViewModel::class.java]

        model.articles.observe(this, Observer{
            adapter.articles = it
            adapter.notifyDataSetChanged()
        })

        setupRecyclerView()
        model.getNews()
    }

    fun setupRecyclerView() {
        adapter = MyAdapter(this, ArrayList<Article>())
        val rv = findViewById<View>(R.id.rv) as RecyclerView
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)
    }
}

