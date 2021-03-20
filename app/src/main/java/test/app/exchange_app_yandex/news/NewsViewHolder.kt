package test.app.exchange_app_yandex.news

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import test.app.exchange_app_yandex.R

class NewsViewHolder(v: View): RecyclerView.ViewHolder(v) {

    val headLine: TextView = v.findViewById(R.id.headline_news)
    val summary: TextView = v.findViewById(R.id.summary_news)
    val url: TextView = v.findViewById(R.id.url_news)
    val image: ImageView = v.findViewById(R.id.image_news)
}