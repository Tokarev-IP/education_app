package test.app.exchange_app_yandex.list

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import test.app.exchange_app_yandex.api.Api
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import test.app.exchange_app_yandex.data.QuoteResponse
import test.app.exchange_app_yandex.data.StockProfileTwoResponse
import test.app.exchange_app_yandex.db.DaoConstituents
import test.app.exchange_app_yandex.db.DataConstituents
import test.app.exchange_app_yandex.db.DataQuote
import test.app.exchange_app_yandex.db.DataStockProfileTwo

class ListRepository(private val db: DaoConstituents, private val dataViewModel: ListViewModel) {

    @SuppressLint("CheckResult")
    fun getFactoryData(symbol: String, token: String) {
        db.getAllConstituents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.count() == 0) {
                        getApiIndices(symbol, token)
                                .subscribe({ data ->

                                    for (element in data.constituents)
                                        insertConstituent(element, false)
                                }, { error ->
                                    Log.e("ListRepository ERROR", error.toString())
                                })
                    }
                }, { error ->
                    Log.e("ListRepository ERROR", error.toString())
                })

        val data = db.getFactoryAllConstituents()
        dataViewModel.setData(data)
    }

    private fun getApiIndices(symbol: String, token: String) =
            Api.apiClient.getIndicesConstituents(symbol, token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())

    private fun insertConstituent(element: String, bool: Boolean) =
            db.insertConstituents(DataConstituents(element, bool))
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe({}, { er->
                        Log.e("ListRepository ERROR", er.toString())
                    })

    fun updateFav(constituents: DataConstituents) =
            db.updateFav(constituents)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

    fun updateConstituents(constituents: String, bool: Boolean) =
            db.update(constituents, bool)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

    @SuppressLint("CheckResult")
    fun insertStockProfilTwo(constituent: String, data: StockProfileTwoResponse) {
        db.insertStockProfilTwo(
                DataStockProfileTwo(
                        constituent,
                        data .country,
                        data .currency,
                        data .name,
                        data .market_capitalization,
                        if(data .web_url !="") data .web_url else null,
                        if(data .logo !="") data .logo else null,
                        if(data .finnhub_industry !="") data .finnhub_industry else null
                ))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                }, {
                    Log.e("ADAPTER ERROR", it.toString())
                })
    }

    fun countDeltaPrice(current: Float, open: Float): String{
        return if (current / open -1 > 0)
            "+"+String.format("%.2f",(current / open -1)*100)+"%"
        else
            String.format("%.2f",(current / open -1)*100)+"%"
    }

    fun countDeltaColor(current: Float, open: Float): Int {
        return if (current / open -1 > 0)
            Color.GREEN
        else
            Color.RED
    }

    @SuppressLint("CheckResult")
    fun updateQuote(constituent: String, data: QuoteResponse) {
        db.updateQuote(DataQuote(constituent, data.open, data.current,
                data.low, data.high))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({},{
                    Log.e("ADAPTER ERROR", it.toString())
                })
    }

    @SuppressLint("CheckResult")
    fun getDbQuote(constituent: String) =
        db.getQuote(constituent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    fun getApiQuote(constituent: String, token: String) =
            Api.apiClient.getQuote(constituent, token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())


    @SuppressLint("CheckResult")
    fun insertQuote(constituent: String, price: QuoteResponse){
        db.insertQuote(DataQuote(constituent, price.open, price.current,
                price.low, price.high))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({},{
                    Log.e("ADAPTER ERROR", it.toString())
                })
    }

    fun getApiStockProfil(constituent: String, token: String) =
            Api.apiClient.getStockProfileTwo(constituent, token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

    fun getDbStockProfil(constituent: String) =
            db.getStockProfilTWO(constituent)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
}