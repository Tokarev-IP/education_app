package test.app.exchange_app_yandex.list

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import test.app.exchange_app_yandex.R
import test.app.exchange_app_yandex.api.Api
import test.app.exchange_app_yandex.db.DataConstituents
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import test.app.exchange_app_yandex.db.DaoConstituents
import test.app.exchange_app_yandex.db.DataStockProfileTwo
import java.text.DateFormat
import kotlin.properties.Delegates

class ListAdapter(private val db: DaoConstituents) : RecyclerView.Adapter<ListViewHolder>() {

    private val TYPE_FIRST = 0
    private val TYPE_SECOND = 1
    var layoutId by Delegates.notNull<Int>()
    private val TOKEN = "c114bi748v6t4vgvsoj0"
    private var listConstituents: List<DataConstituents> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        if (viewType==TYPE_FIRST)
            layoutId = R.layout.list_item
        else layoutId = R.layout.list_item_second
        val view = LayoutInflater
                .from(parent.context)
                .inflate(layoutId, parent, false)
        return ListViewHolder(view)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.symbol.text = listConstituents[position].constituents

        if (!listConstituents[position].favorite) holder.fav.setBackgroundResource(R.drawable.ic_baseline_star_border_40_gray)

        holder.fav.setOnClickListener {
            it.setBackgroundResource(R.drawable.ic_baseline_star_40_yellow)
        }

        db.getStockProfilTWO(listConstituents[position].constituents)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.count() == 0) {
                        Api.apiClient.getStockProfileTwo(listConstituents[position].constituents, TOKEN)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({ data ->

                                    holder.name.text = data.name

                                    Picasso.get()
                                            .load(data.logo)
                                            .placeholder(R.drawable.ic_baseline_image_search_50)
                                            .error(R.drawable.ic_baseline_image_search_50)
                                            .into(holder.logo)

                                    db.insertStockProfilTwo(DataStockProfileTwo(listConstituents[position].constituents, data.country,
                                            data.currency, data.name, data.market_capitalization,
                                            data.web_url, data.logo, data.finnhub_industry))
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(Schedulers.io())
                                            .subscribe({

                                            }, { er ->
                                                Log.e("ADAPTER ERROR", er.toString())
                                            })

                                           },
                                        { error ->
                                    Log.e("ADAPTER ERROR", error.toString())
                                           })
                    }
                    else {
                        holder.name.text = it[0].name

                        if (it[0].logo!="")
                        Picasso.get()
                                .load(it[0].logo)
                                .placeholder(R.drawable.ic_baseline_image_search_50)
                                .error(R.drawable.ic_baseline_image_search_50)
                                .into(holder.logo)
                        else holder.logo.setBackgroundResource(R.drawable.ic_baseline_image_search_50)
                    }

                },{
                    Log.e("ADAPTER ERROR", it.toString())
                })
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0 ) TYPE_FIRST else TYPE_SECOND
    }

    override fun getItemCount(): Int {
        return listConstituents.size
    }

    fun setData(data: List<DataConstituents>){
        listConstituents = data
        notifyDataSetChanged()
    }

}