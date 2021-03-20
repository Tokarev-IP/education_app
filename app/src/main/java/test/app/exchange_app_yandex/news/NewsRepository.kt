package test.app.exchange_app_yandex.news

import android.annotation.SuppressLint
import android.util.Log
import test.app.exchange_app_yandex.api.Api
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

class NewsRepository(private val newsViewModel: NewsViewModel) {

    @SuppressLint("CheckResult")
    fun getNews(category: String, token: String){
        Api.apiClient.getNews(category, token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                       newsViewModel.setData(it)
            },{
                Log.e("NEWS REP ERROR", it.toString())
            })
    }
}