package test.app.exchange_app_yandex.list

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.squareup.picasso.Picasso
import test.app.exchange_app_yandex.R
import test.app.exchange_app_yandex.chart.ChartFragment
import test.app.exchange_app_yandex.db.DataConstituents
import kotlin.properties.Delegates

class ListAdapter(private val context: AppCompatActivity,
                  private val repList: ListRepository):
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
                repList.updateFav(DataConstituents(itemInfo.constituents, true))
                        .subscribe({
                            it.setBackgroundResource(R.drawable.ic_baseline_star_40_yellow)
                        },{
                            Log.e("ListRepository ERROR", it.toString())
                        })
            }
            else{
                holder.prBar.visibility = View.VISIBLE
                repList.updateFav(DataConstituents(itemInfo.constituents, false))
                        .subscribe({
                            it.setBackgroundResource(R.drawable.ic_baseline_star_border_40_gray)
                        },{
                            Log.e("ListRepository ERROR", it.toString())
                        })
            }
        }
            repList.getDbStockProfil(itemInfo.constituents)
                    .subscribe({ it ->
                        if (it.count() == 0) {
                            repList.getApiStockProfil(itemInfo.constituents, TOKEN)
                                .subscribe({ data ->

                                    repList.insertStockProfilTwo(itemInfo.constituents, data)

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

                            repList.getApiQuote(itemInfo.constituents, TOKEN)
                                    .subscribe({ price->

                                        repList.insertQuote(itemInfo.constituents, price)

                                        holder.price.text = price.current.toString() + " $"
                                        holder.delta.text = repList.countDeltaPrice(price.current,
                                                price.open)
                                        holder.delta.setTextColor(repList.countDeltaColor(price.current,
                                                price.open))
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

                            repList.getApiQuote(itemInfo.constituents, TOKEN)
                                    .subscribe({ apiPrice->

                                        repList.updateQuote(itemInfo.constituents, apiPrice)
                                        holder.price.text = apiPrice.current.toString() + " $"
                                        holder.delta.text = repList.countDeltaPrice(apiPrice.current, apiPrice.open)
                                        holder.delta.setTextColor(repList.countDeltaColor(apiPrice.current, apiPrice.open))
                                        holder.prBar.visibility = View.INVISIBLE

                                    },{ error->
                                        repList.getDbQuote(itemInfo.constituents)
                                                .subscribe({ dbPrice->

                                                    if(dbPrice.count() != 0) {
                                                        holder.price.text = dbPrice[0].current.toString() + " $"
                                                        holder.delta.text = repList.countDeltaPrice(dbPrice[0].current,
                                                                dbPrice[0].open)
                                                        holder.delta.setTextColor(repList.countDeltaColor(dbPrice[0].current,
                                                                dbPrice[0].open))
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