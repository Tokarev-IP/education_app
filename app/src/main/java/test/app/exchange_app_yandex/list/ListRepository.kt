package test.app.exchange_app_yandex.list

import test.app.exchange_app_yandex.api.Api
import test.app.exchange_app_yandex.api.ApiInterface
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

class ListRepository {

    fun getGSPC(symbol: String, token: String){
        Api.apiClient.getIndicesConstituents(symbol, token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            },{

            })
    }
}