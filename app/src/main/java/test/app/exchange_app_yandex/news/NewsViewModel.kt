package test.app.exchange_app_yandex.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import test.app.exchange_app_yandex.data.MarketNewsResponse

class NewsViewModel: ViewModel() {

    private val newsLiveData: MutableLiveData<List<MarketNewsResponse>> = MutableLiveData()

    fun getData() = newsLiveData

    fun setData(data: List<MarketNewsResponse>){
        newsLiveData.value = data
    }
}