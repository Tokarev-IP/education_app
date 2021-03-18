package test.app.exchange_app_yandex.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import test.app.exchange_app_yandex.db.DataConstituents

class ListViewModel: ViewModel() {

//    var constituentsVM: MutableLiveData<List<DataConstituents>> = MutableLiveData()
//
//
//    fun getConstituents() = constituentsVM
//
//    fun setConstituents(data: List<DataConstituents>){
//        constituentsVM.value = data
//    }


    lateinit var dataFactory: DataSource.Factory<Int, DataConstituents>

    private val config = PagedList.Config.Builder()
            .setPageSize(8)
            .setInitialLoadSizeHint(16)
            .setPrefetchDistance(8)
            .setEnablePlaceholders(false)
            .build()

    fun getData(): LiveData<PagedList<DataConstituents>> {
        return LivePagedListBuilder(dataFactory, config)
                .build()
    }

    fun setData(data: DataSource.Factory<Int, DataConstituents>){
        dataFactory = data
    }






}