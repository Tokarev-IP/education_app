package test.app.exchange_app_yandex.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import test.app.exchange_app_yandex.db.DataConstituents

class ListViewModel: ViewModel() {

    var constituentsVM: MutableLiveData<List<DataConstituents>> = MutableLiveData()

    fun getConstituents() = constituentsVM

    fun setConstituents(data: List<DataConstituents>){
        constituentsVM.value = data
    }
}