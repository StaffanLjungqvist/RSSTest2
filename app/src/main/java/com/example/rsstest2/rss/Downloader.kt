package com.example.rsstest2.rss

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.rsstest2.TAG
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection

class Downloader(var c: Context, var urlAddress: String, var rv: RecyclerView) :
    AsyncTask<Void?, Void?, Any>() {
    var pd: ProgressDialog? = null
    override fun onPreExecute() {
        Log.d(TAG, "startar downloader")
        super.onPreExecute()
        pd = ProgressDialog(c)
        pd!!.setTitle("Fetch Article")
        pd!!.setMessage("Fetching...Please wait")
        pd!!.show()
    }



    override fun onPostExecute(data: Any) {
        super.onPostExecute(data)
        pd!!.dismiss()
        if (data.toString().startsWith("Error")) {
            Toast.makeText(c, data.toString(), Toast.LENGTH_SHORT).show()
        } else {
            //PARSE
            RSSParser(c, (data as InputStream), rv).execute()
        }
    }

    private fun downloadData(): Any {
        val connection: Any = Connector.connect(urlAddress)
        return if (connection.toString().startsWith("Error")) {
            connection.toString()
        } else try {
            val con = connection as HttpURLConnection
            val responseCode = con.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Log.d(TAG, "Någonting funkade")
                return BufferedInputStream(con.inputStream)
            }
            ErrorTracker.RESPONSE_EROR + con.responseMessage
        } catch (e: IOException) {
            e.printStackTrace()
            ErrorTracker.IO_EROR
        }
    }

    override fun doInBackground(vararg params: Void?): Any {
        return downloadData()
    }
}