package test.app.exchange_app_yandex.favorite

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import test.app.exchange_app_yandex.db.DaoConstituents
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.Single
import test.app.exchange_app_yandex.api.Api
import test.app.exchange_app_yandex.data.QuoteResponse
import test.app.exchange_app_yandex.db.DataConstituents
import test.app.exchange_app_yandex.db.DataQuote

class FavoriteRepository(private val db: DaoConstituents, private val favViewModel: FavoriteViewModel) {

    @SuppressLint("CheckResult")
    fun getFavorite(){
        db.getFavoriteAllConstituents()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                            fromListToArray(it)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe({ array->
                                        favViewModel.setData(array)
                                    },{ error->
                                        Log.e("FAV REP ERROR", error.toString())
                                    })
                },{
                    Log.e("FAV REP ERROR", it.toString())
                })
    }

    private fun fromListToArray(list: List<DataConstituents>): Single<ArrayList<DataConstituents>> {
        val array: ArrayList<DataConstituents> = ArrayList()
        for (element in list)
            array.add(element)
        return Single.create {
            it.onSuccess(array)
        }
    }

    @SuppressLint("CheckResult")
    fun findFavElements(editData: String){
        db.findConstituentFavorite(editData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    fromListToArray(it)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ array->
                                favViewModel.setData(array)
                            },{})
                },{
                    Log.e("FAV REP ERROR", it.toString())
                })
    }

    @SuppressLint("CheckResult")
    fun updateQuote(apiPrice : QuoteResponse, constituents: String){
        db.updateQuote(DataQuote(constituents, apiPrice.open, apiPrice.current,
                apiPrice.low, apiPrice.high))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({},{})
    }

    @SuppressLint("CheckResult")
    fun updateConstituents(constituents: String, boolean: Boolean ){
        db.update(constituents, boolean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({},{})
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

    fun getQuote(constituents: String) =
            db.getQuote(constituents)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

    fun getApiQuote(constituents: String, token: String) =
            Api.apiClient.getQuote(constituents, token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

    fun getStockProfilTwo(constituents: String) =
            db.getStockProfilTWO(constituents)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
}