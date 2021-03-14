package test.app.exchange_app_yandex.list

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import test.app.exchange_app_yandex.R
import test.app.exchange_app_yandex.api.Api
import test.app.exchange_app_yandex.db.DataConstituents
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

class ListAdapter: RecyclerView.Adapter<ListViewHolder>() {

    private val TOKEN = "c114bi748v6t4vgvsoj0"
    private var listConstituents: List<DataConstituents> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        return ListViewHolder(view)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.symbol.text = listConstituents[position].constituents

        Api.apiClient.getStockProfileTwo(listConstituents[position].constituents, TOKEN )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    holder.name.text = it.name
                },{
                    Log.e("ADAPTER ERROR", it.toString())
                })
    }

    override fun getItemCount(): Int {
        return listConstituents.size
    }

    fun setData(data: List<DataConstituents>){
        listConstituents = data
        notifyDataSetChanged()
    }

}