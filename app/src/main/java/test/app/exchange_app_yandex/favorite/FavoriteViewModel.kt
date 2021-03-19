package test.app.exchange_app_yandex.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import test.app.exchange_app_yandex.db.DataConstituents

class FavoriteViewModel: ViewModel() {

    val dataLiveData: MutableLiveData<List<DataConstituents>> = MutableLiveData()
}