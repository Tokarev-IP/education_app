package test.app.exchange_app_yandex.news

import android.text.util.Linkify
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import test.app.exchange_app_yandex.R
import test.app.exchange_app_yandex.data.MarketNewsResponse


class NewsAdapter(): RecyclerView.Adapter<NewsViewHolder>() {

    private var newsDataList: List<MarketNewsResponse> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val data: MarketNewsResponse = newsDataList[position]

        holder.headLine.text = data.head_line
        holder.summary.text = data.summary
        holder.url.text = data.url
        Linkify.addLinks(holder.url, Linkify.WEB_URLS)

        data.image.let { logo->
            if (logo != "")
                Picasso.get()
                    .load(logo)
                    .placeholder(R.drawable.ic_baseline_image_search_50)
                    .error(R.drawable.ic_baseline_image_not_supported_24)
                    .resize(720, 480)
                    .centerCrop()
                    .noFade()
                    .into(holder.image)
        }
    }

    override fun getItemCount(): Int {
        return newsDataList.size
    }

    fun setData(data: List<MarketNewsResponse>){
        newsDataList = data
        notifyDataSetChanged()
    }


}