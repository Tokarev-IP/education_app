package test.app.exchange_app_yandex.chart

import android.annotation.SuppressLint
import android.util.Log
import com.github.mikephil.charting.data.Entry
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import test.app.exchange_app_yandex.api.Api
import test.app.exchange_app_yandex.data.StockCandleResponse

class ChartRepository(private val chartViewModel: ChartViewModel) {

    @SuppressLint("CheckResult")
    fun getStockCandle(symbol: String, resolution: String, from: Long, to: Long, token: String){
        Api.apiClient.getStockCandle(symbol, resolution, from, to, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    transformToEntry(it)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ data->
                                chartViewModel.setData(data)
                            },{

                            })
                },{
                    Log.e("Api ERROR", it.toString())
                })
    }

    fun transformToEntry(data: StockCandleResponse): Single<ArrayList<Entry>>{
        val array: ArrayList<Entry> = ArrayList()
        for (i in data.current.indices){
            array.add(Entry(data.time[i].toFloat(), data.current[i].toFloat()))
        }
        return Single.create {
            it.onSuccess(array)
        }
    }
}