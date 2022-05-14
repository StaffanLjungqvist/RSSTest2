package com.example.rsstest2.rss

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.rsstest2.TAG
import com.example.rsstest2.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection

class Downloader(var urlAddress: String) {

    suspend fun downloadData(): ArrayList<Article> {

        var articles = arrayListOf<Article>()

        withContext(Dispatchers.IO) {
            val connection: Any = Connector.connect(urlAddress)
            val con = connection as HttpURLConnection
            val data = BufferedInputStream(con.inputStream)
            articles = RSSParser(data as InputStream).parseRSS()

        }
        Log.d(TAG, "downloadData skickar med ${articles.size} stycken artiklar")
        return articles
    }
}

