package com.example.rsstest2

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rsstest2.model.Article
import com.example.rsstest2.rss.Downloader
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    val articles: MutableLiveData<ArrayList<Article>> by lazy {
        MutableLiveData<ArrayList<Article>>()
    }

    val urlAddress = "https://smaforetagarna.se/nyheter/feed/"

    fun getNews() {
        viewModelScope.launch {
            val result = Downloader(urlAddress).downloadData()
            articles.value = result
        }

    }
}