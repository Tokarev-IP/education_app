package test.app.exchange_app_yandex.list

import android.annotation.SuppressLint
import android.util.Log
import android.widget.EditText
import test.app.exchange_app_yandex.api.Api
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import test.app.exchange_app_yandex.db.DaoConstituents
import test.app.exchange_app_yandex.db.DataConstituents

class ListRepository(private val db: DaoConstituents, private val dataViewModel: ListViewModel) {

    @SuppressLint("CheckResult")
    fun getFactoryData(symbol: String, token: String){
        db.getAllConstituents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                           if (it.count()==0) {
                               Log.d("LIST", "List.count = 0")
                               Api.apiClient.getIndicesConstituents(symbol, token)
                                       .subscribeOn(Schedulers.io())
                                       .observeOn(Schedulers.io())
                                       .subscribe({ data->
                                           Log.d("Insert", data.constituents.size.toString())

                                           for (element in data.constituents)
                                               db.insertConstituents(DataConstituents(element, false))
                                                       .subscribeOn(Schedulers.io())
                                                       .observeOn(Schedulers.io())
                                                       .subscribe({},{})

                                           Log.d("Insert", "insert was")
                                       },{ error->
                                           Log.e("ListRepository ERROR", error.toString())
                                       })
                           }

                },{ error->
                    Log.e("ListRepository ERROR", error.toString())
                })

        val data = db.getFactoryAllConstituents()
        dataViewModel.setData(data)
    }

    @SuppressLint("CheckResult")
    fun findListElements(editText: String){
        val data = db.findConstituent(editText)
        dataViewModel.setData(data)
    }
}