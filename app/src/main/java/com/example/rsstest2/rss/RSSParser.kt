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


class RSSParser(c: Context, `is`: InputStream, rv: RecyclerView) :
    AsyncTask<Void?, Void?, Boolean>() {
    var c: Context
    var `is`: InputStream
    var rv: RecyclerView
    var pd: ProgressDialog? = null
    var articles: ArrayList<Article> = ArrayList()
    override fun onPreExecute() {
        super.onPreExecute()
        pd = ProgressDialog(c)
        pd!!.setTitle("Parse")
        pd!!.setMessage("Parsing...Please wait")
        pd!!.show()
    }



    override fun onPostExecute(isParsed: Boolean) {
        super.onPostExecute(isParsed)
        pd!!.dismiss()
        if (isParsed) {
            //BIND
            rv.adapter = MyAdapter(articles)
        } else {
            Toast.makeText(c, "Unable To Parse", Toast.LENGTH_SHORT).show()
        }
    }

    private fun parseRSS(): Boolean {
        try {
            Log.d(TAG, "Parsing RSS")
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            parser.setInput(`is`, null)
            var event = parser.eventType
            var tagValue: String? = null
            var isSiteMeta = true
            articles.clear()
            var article = Article("", "", "", "")
            do {
                val tagName = parser.name
                when (event) {
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
                                article.description = desc!!.substring(desc.indexOf("/>") + 2)

                                //EXTRACT IMAGE FROM DESC
/*
                                val imageUrl = desc!!.substring(
                                    desc.indexOf("src=") + 5,
                                    desc.indexOf("jpg") + 3
                                )
*/

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
            return true
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }

    init {
        this.c = c
        this.`is` = `is`
        this.rv = rv
    }

    override fun doInBackground(vararg params: Void?): Boolean {
        return parseRSS()
    }
}