package test.app.exchange_app_yandex.chart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.Entry
import test.app.exchange_app_yandex.data.StockCandleResponse
import java.security.KeyStore

class ChartViewModel: ViewModel() {

    val chartData: MutableLiveData<ArrayList<Entry>> = MutableLiveData()

    fun getData() = chartData

    fun setData(data: ArrayList<Entry>){
        chartData.value = data
    }
}