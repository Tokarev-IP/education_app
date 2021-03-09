package test.app.exchange_app_yandex.list

import android.annotation.SuppressLint
import android.util.Log
import test.app.exchange_app_yandex.api.Api
import io.reactivex.schedulers.Schedulers
import test.app.exchange_app_yandex.db.DataConstituents
import test.app.exchange_app_yandex.db.DbConstituents

class ListRepository(val db: DbConstituents) {

    @SuppressLint("CheckResult")
    fun getGSPCtoROOM(symbol: String, token: String){
        Api.apiClient.getIndicesConstituents(symbol, token)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                for (i in 0..it.constituents.size)
                       db.movieDao().insertConstituents(DataConstituents(it.constituents[i], false))
            },{
                    it -> Log.e("ListRepository ERROR", it.toString())
            })
    }






}