package com.example.rsstest2



import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rsstest2.model.Article


class MyAdapter(var context: Context, var articles: ArrayList<Article>) :
    RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val (title, desc, date, imageUrl1, content, link) = articles[position]

        Glide.with(context)
            .load(imageUrl1)
            .into(holder.img)

        holder.titleTxt.text = title
        holder.desctxt.text = Html.fromHtml(desc as String?).toString()
        holder.dateTxt.text = date
        holder.btnLink.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(context, browserIntent, null)
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleTxt = itemView.findViewById<TextView>(R.id.titleTxt)
        val desctxt = itemView.findViewById<TextView>(R.id.descTxt)
        val dateTxt = itemView.findViewById<TextView>(R.id.dateTxt)
        val img = itemView.findViewById<ImageView>(R.id.articleImage)
        val btnLink = itemView.findViewById<Button>(R.id.btnLink)

}