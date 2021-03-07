package test.app.exchange_app_yandex.list

import android.annotation.SuppressLint
import android.util.Log
import test.app.exchange_app_yandex.api.Api
import test.app.exchange_app_yandex.api.ApiInterface
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import test.app.exchange_app_yandex.db.DbConstituents

class ListRepository(val db: DbConstituents) {

    @SuppressLint("CheckResult")
    fun getGSPC(symbol: String, token: String){
        Api.apiClient.getIndicesConstituents(symbol, token)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                db.movieDao().insertConstituents(it)
            },{
                    it -> Log.e("ListRepository ERROR", it.toString())
            })
    }

    
}