package test.app.exchange_app_yandex.favorite

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import test.app.exchange_app_yandex.R
import test.app.exchange_app_yandex.api.Api
import test.app.exchange_app_yandex.db.DaoConstituents
import test.app.exchange_app_yandex.db.DataConstituents
import test.app.exchange_app_yandex.db.DataQuote
import kotlin.properties.Delegates

class FavoriteAdapter(private val db: DaoConstituents): RecyclerView.Adapter<FavoriteViewHolder>() {

    private var dataList: List<DataConstituents> = emptyList()
    private val TYPE_FIRST = 0
    private val TYPE_SECOND = 1
    var layoutId by Delegates.notNull<Int>()
    private val TOKEN = "c114bi748v6t4vgvsoj0"
    lateinit var prdelta: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        if (viewType == TYPE_FIRST)
            layoutId = R.layout.list_item
        else layoutId = R.layout.list_item_second
        val view = LayoutInflater
                .from(parent.context)
                .inflate(layoutId, parent, false)
        return FavoriteViewHolder(view)
    }

    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.symbol.text = dataList[position].constituents

        db.getStockProfilTWO(dataList[position].constituents)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    holder.name.text = it[0].name

                    it[0].logo?.let { logo->
                        Picasso.get()
                                .load(logo)
                                .placeholder(R.drawable.ic_baseline_image_search_50)
                                .error(R.drawable.ic_baseline_image_not_supported_24)
                                .into(holder.logo)
                    }

                },{
                    Log.e("FAVORITE ADAPTER ERROR", it.toString())
                })

        Api.apiClient.getQuote(dataList[position].constituents, TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ apiPrice->

                    db.updateQuote(DataQuote(dataList[position].constituents, apiPrice.open, apiPrice.current,
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
                    db.getQuote(dataList[position].constituents)
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
                })
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0 ) TYPE_FIRST else TYPE_SECOND
    }
}