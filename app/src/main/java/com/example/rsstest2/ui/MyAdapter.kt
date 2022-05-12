package com.example.rsstest2



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rsstest2.model.Article


class MyAdapter(var articles: ArrayList<Article>) :
    RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val (title, desc, date, imageUrl1) = articles[position]
//        val imageUrl = imageUrl1!!.replace("localhost", "10.0.2.2")
        holder.titleTxt.setText(title)
        holder.desctxt.setText(desc!!.substring(0, 130))
        holder.dateTxt.setText(date)
     //   PicassoClient.downloadImage(c, imageUrl, holder.img)
    }

    override fun getItemCount(): Int {
        return articles.size
    }
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var titleTxt: TextView
    var desctxt: TextView
    var dateTxt: TextView
    var img: ImageView

    init {
        titleTxt = itemView.findViewById<View>(R.id.titleTxt) as TextView
        desctxt = itemView.findViewById<View>(R.id.descTxt) as TextView
        dateTxt = itemView.findViewById<View>(R.id.dateTxt) as TextView
        img = itemView.findViewById<View>(R.id.articleImage) as ImageView
    }
}