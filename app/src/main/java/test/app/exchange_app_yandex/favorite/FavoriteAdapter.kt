package test.app.exchange_app_yandex.favorite

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import test.app.exchange_app_yandex.R
import test.app.exchange_app_yandex.chart.ChartFragment
import test.app.exchange_app_yandex.db.DataConstituents
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class FavoriteAdapter(private val context: AppCompatActivity, private val repFav: FavoriteRepository):
        RecyclerView.Adapter<FavoriteViewHolder>() {

    private var dataList: ArrayList<DataConstituents> = ArrayList()

    private val TYPE_FIRST = 0
    private val TYPE_SECOND = 1
    var layoutId by Delegates.notNull<Int>()
    private val TOKEN = "c114bi748v6t4vgvsoj0"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        layoutId = if (viewType == TYPE_FIRST)
            R.layout.list_item
        else R.layout.list_item_second
        val view = LayoutInflater
                .from(parent.context)
                .inflate(layoutId, parent, false)
        return FavoriteViewHolder(view)
    }

    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.prBar.visibility = View.VISIBLE

        holder.symbol.text = dataList[position].constituents

        holder.fav.setBackgroundResource(R.drawable.ic_baseline_star_40_yellow)

        val chartView = ChartFragment(dataList[position].constituents)

        holder.item.setOnClickListener {
            context.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, chartView)
                    .addToBackStack(null)
                    .commit()
        }

        holder.fav.setOnClickListener {
            repFav.updateConstituents(dataList[position].constituents, false)
            dataList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount - position)
        }

        repFav.getStockProfilTwo(dataList[position].constituents)
                .subscribe({
                    if (it.count() != 0) {
                        holder.name.text = it[0].name

                        it[0].logo?.let { logo ->
                            Picasso.get()
                                    .load(logo)
                                    .placeholder(R.drawable.ic_baseline_image_search_50)
                                    .error(R.drawable.ic_baseline_image_not_supported_24)
                                    .into(holder.logo)
                        }
                    }
                           }, {
                    Log.e("FAVORITE ADAPTER ERROR", it.toString())
                           })

        repFav.getApiQuote(dataList[position].constituents, TOKEN)
                .subscribe({ apiPrice ->

                    repFav.updateQuote(apiPrice, dataList[position].constituents)
                    holder.price.text = apiPrice.current.toString() + " $"
                    holder.delta.text = repFav.countDeltaPrice(apiPrice.current, apiPrice.open)
                    holder.delta.setTextColor(repFav.countDeltaColor(apiPrice.current, apiPrice.open))
                    holder.prBar.visibility = View.INVISIBLE

                    }, { error ->

                    repFav.getQuote(dataList[position].constituents)
                            .subscribe({
                                if (it.count() != 0) {

                                    holder.price.text = it[0].current.toString() + " $"
                                    holder.delta.text = repFav.countDeltaPrice(it[0].current, it[0].open)
                                    holder.delta.setTextColor(repFav.countDeltaColor(it[0].current, it[0].open))
                                    holder.prBar.visibility = View.INVISIBLE

                                }
                                       }, {
                                Log.e("ADAPTER ERROR", it.toString())
                                       })
                    Log.e("ADAPTER ERROR", error.toString())
                    })
    }


    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0 ) TYPE_FIRST else TYPE_SECOND
    }

    fun setListData(data: ArrayList<DataConstituents>){
        dataList = data
        notifyDataSetChanged()
    }
}