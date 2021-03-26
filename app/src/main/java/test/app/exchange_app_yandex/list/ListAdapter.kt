package test.app.exchange_app_yandex.list

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import test.app.exchange_app_yandex.R
import test.app.exchange_app_yandex.api.Api
import test.app.exchange_app_yandex.chart.ChartFragment
import test.app.exchange_app_yandex.chart.ChartRepository
import test.app.exchange_app_yandex.db.DaoConstituents
import test.app.exchange_app_yandex.db.DataConstituents
import test.app.exchange_app_yandex.db.DataQuote
import test.app.exchange_app_yandex.db.DataStockProfileTwo
import kotlin.properties.Delegates

class ListAdapter(private val db: DaoConstituents, private val context: AppCompatActivity):
    PagedListAdapter<DataConstituents, ListViewHolder>(DIFF_CALLBACK) {

    private val TYPE_FIRST = 0
    private val TYPE_SECOND = 1
    var layoutId by Delegates.notNull<Int>()
    private val TOKEN = "c114bi748v6t4vgvsoj0"
    lateinit var prdelta: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        layoutId = if (viewType == TYPE_FIRST)
            R.layout.list_item
        else R.layout.list_item_second
        val view = LayoutInflater
                .from(parent.context)
                .inflate(layoutId, parent, false)
        return ListViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("CheckResult", "ResourceAsColor", "SetTextI18n", "ShowToast")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        Log.e("NUMBER", position.toString())

        getItem(position)?.let { itemInfo ->

            val chartView = ChartFragment(itemInfo.constituents)

            holder.item.setOnClickListener {
                context.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, chartView)
                        .addToBackStack(null)
                        .commit()
            }

        holder.symbol.text = itemInfo.constituents

        if (!itemInfo.favorite) holder.fav.setBackgroundResource(R.drawable.ic_baseline_star_border_40_gray)
        else holder.fav.setBackgroundResource(R.drawable.ic_baseline_star_40_yellow)

        holder.fav.setOnClickListener {

            if (!itemInfo.favorite) {
                holder.prBar.visibility = View.VISIBLE
                db.update(itemInfo.constituents, true)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            it.setBackgroundResource(R.drawable.ic_baseline_star_40_yellow)
                        },{})
            }
            else{
                holder.prBar.visibility = View.VISIBLE
                db.update(itemInfo.constituents, false)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            it.setBackgroundResource(R.drawable.ic_baseline_star_border_40_gray)
                        },{})
            }
        }

            db.getStockProfilTWO(itemInfo.constituents)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ it ->
                        if (it.count() == 0) {
                            Api.apiClient.getStockProfileTwo(itemInfo.constituents, TOKEN)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({ data ->

                                    db.insertStockProfilTwo(
                                            DataStockProfileTwo(
                                                    itemInfo.constituents,
                                                    data.country,
                                                    data.currency,
                                                    data.name,
                                                    data.market_capitalization,
                                                    if(data.web_url !="") data.web_url else null,
                                                    if(data.logo !="") data.logo else null,
                                                    if(data.finnhub_industry !="") data.finnhub_industry else null
                                        )
                                    )
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(Schedulers.io())
                                        .subscribe({
                                        }, { er ->
                                            Log.e("ADAPTER ERROR", er.toString())
                                        })

                                    holder.name.text = data.name

                                    data.logo?.let { logo->
                                        if (logo != "")
                                        Picasso.get()
                                                .load(logo)
                                                .placeholder(R.drawable.ic_baseline_image_search_50)
                                                .error(R.drawable.ic_baseline_image_not_supported_24)
                                                .into(holder.logo)
                                    }
                                },
                                    { error ->
                                        Log.e("ADAPTER ERROR", error.toString())
                                        holder.prBar.visibility = View.VISIBLE
                                    })

                            Api.apiClient.getQuote(itemInfo.constituents, TOKEN)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe({ price->

                                        db.insertQuote(DataQuote(itemInfo.constituents, price.open, price.current,
                                                price.low, price.high))
                                                .subscribeOn(Schedulers.io())
                                                .subscribe({},{})

                                        holder.price.text = price.current.toString() + " $"

                                        if (price.current / price.open -1 > 0) {
                                            prdelta = "+"+String.format("%.2f",(price.current / price.open -1)*100)+"%"
                                            holder.delta.text = prdelta
                                            holder.delta.setTextColor(Color.GREEN)
                                        }
                                        else {
                                            prdelta = String.format("%.2f",(price.current / price.open -1)*100)+"%"
                                            holder.delta.text = prdelta
                                            holder.delta.setTextColor(Color.RED)
                                        }

                                        holder.prBar.visibility = View.INVISIBLE


                                    },{
                                        Log.e("ADAPTER ERROR", it.toString())
                                        holder.prBar.visibility = View.VISIBLE
                                    })
                        } else {

                            holder.name.text = it[0].name

                            it[0].logo?.let {
                                Picasso.get()
                                        .load(it)
                                        .placeholder(R.drawable.ic_baseline_image_search_50)
                                        .error(R.drawable.ic_baseline_image_not_supported_24)
                                        .into(holder.logo)
                            }

                            Api.apiClient.getQuote(itemInfo.constituents, TOKEN)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe({ apiPrice->

                                        db.updateQuote(DataQuote(itemInfo.constituents, apiPrice.open, apiPrice.current,
                                                apiPrice.low, apiPrice.high))
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe({

                                                    holder.price.text = apiPrice.current.toString() + " $"

                                                    if (apiPrice.current / apiPrice.open -1 > 0) {
                                                        holder.delta.text = "+"+String.format("%.2f",(apiPrice.current / apiPrice.open -1)*100)+"%"
                                                        holder.delta.setTextColor(Color.GREEN)
                                                    }
                                                    else {
                                                        holder.delta.setTextColor(Color.RED)
                                                        holder.delta.text = String.format("%.2f",(apiPrice.current / apiPrice.open -1)*100)+"%"
                                                    }

                                                    holder.prBar.visibility = View.INVISIBLE
                                                },{})


                                    },{ error->
                                        db.getQuote(itemInfo.constituents)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe({

                                                    if(it.count() != 0) {

                                                        holder.price.text = it[0].current.toString() + " $"

                                                        if (it[0].current / it[0].open - 1 > 0) {
                                                            holder.delta.text = "+" + String.format("%.2f", (it[0].current / it[0].open - 1) * 100) + "%"
                                                            holder.delta.setTextColor(Color.GREEN)
                                                        } else {
                                                            holder.delta.setTextColor(Color.RED)
                                                            holder.delta.text = String.format("%.2f", (it[0].current / it[0].open - 1) * 100) + "%"
                                                        }
                                                        holder.prBar.visibility = View.INVISIBLE
                                                    }

                                                },{
                                                    Log.e("ADAPTER ERROR", it.toString())
                                                })

                                        Log.e("ADAPTER ERROR", error.toString())
                                    })
                        }

                    }, {
                        Log.e("ADAPTER ERROR", it.toString())
                    })
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0 ) TYPE_FIRST else TYPE_SECOND
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataConstituents>() {
            override fun areItemsTheSame(
                old: DataConstituents,
                new: DataConstituents
            ): Boolean = old.constituents == new.constituents

            override fun areContentsTheSame(
                old: DataConstituents,
                new: DataConstituents
            ): Boolean = old == new
        }
    }

}