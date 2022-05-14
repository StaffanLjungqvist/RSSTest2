package com.example.rsstest2.rss

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.rsstest2.MyAdapter
import com.example.rsstest2.TAG
import com.example.rsstest2.model.Article
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream


class RSSParser(val inputStream: InputStream) {

    var articles: ArrayList<Article> = ArrayList()

    suspend fun parseRSS(): ArrayList<Article> {
        try {
            Log.d(TAG, "Parsing RSS")
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            parser.setInput(inputStream, null)
            var event = parser.eventType
            var tagValue: String? = null
            var isSiteMeta = true
            articles.clear()
            var article = Article()
            do {
                val tagName = parser.name
                when (event) {


                    //Kollar om en <Tag> har ett visst värde. Om så är fallet så kollas även underkategorier
                    XmlPullParser.START_TAG -> if (tagName.equals("item", ignoreCase = true)) {
                        article = Article()
                        isSiteMeta = false
                    }
                    XmlPullParser.TEXT -> tagValue = parser.text
                    XmlPullParser.END_TAG -> {
                        if (!isSiteMeta) {
                            if (tagName.equals("title", ignoreCase = true)) {
                                article.title = tagValue
                            } else if (tagName.equals("description", ignoreCase = true)) {
                                val desc = tagValue
                                article.description = desc!!.substring(desc.indexOf("/>") + 1)

                                    //Todo - fixa så detta funkar
                                val imageUrl = desc!!.substring(
                                    desc.indexOf("thumbnail url=") + 5,
                                    desc.indexOf("jpg") + 5
                                )

     //                           article.imageUrl = imageUrl
                            } else if (tagName.equals("pubDate", ignoreCase = true)) {
                                article.date = tagValue
                            } 
                        }
                        if (tagName.equals("item", ignoreCase = true)) {
                            articles.add(article)
                            isSiteMeta = true
                        }
                    }
                }
                event = parser.next()
            } while (event != XmlPullParser.END_DOCUMENT)
           Log.d(TAG, "Parse successful!")
            Log.d(TAG, "number of articles = ${articles.size}")
            return articles
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return arrayListOf<Article>()
    }
}