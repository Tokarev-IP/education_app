package test.app.exchange_app_yandex.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import test.app.exchange_app_yandex.db.DataConstituents
import java.util.*
import kotlin.collections.ArrayList

class FavoriteViewModel: ViewModel() {

    private val dataLiveData: MutableLiveData<ArrayList<DataConstituents>> = MutableLiveData()

    fun getData() = dataLiveData

    fun setData(data: ArrayList<DataConstituents>){
        dataLiveData.value = data
    }

}