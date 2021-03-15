package test.app.exchange_app_yandex.list

import android.annotation.SuppressLint
import android.util.Log
import test.app.exchange_app_yandex.api.Api
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import test.app.exchange_app_yandex.db.DaoConstituents
import test.app.exchange_app_yandex.db.DataConstituents

class ListRepository(private val db: DaoConstituents,private val dataViewModel: ListViewModel) {

//    @SuppressLint("CheckResult")
//    fun getGSPCtoROOM(symbol: String, token: String){
//        Api.apiClient.getIndicesConstituents(symbol, token)
//            .subscribeOn(Schedulers.io())
//            .observeOn(Schedulers.io())
//            .subscribe({
//                for (element in it.constituents)
//                       db.insertConstituents(DataConstituents(element, false))
//                               .also {
//                                   db.getAllConstituents()
//                                           .subscribeOn(Schedulers.io())
//                                           .observeOn(AndroidSchedulers.mainThread())
//                                           .subscribe({
//                                               dataViewModel.setConstituents(it)
//                                           },{
//                                               it -> Log.e("ListRepository ERROR", it.toString())
//                                           })
//                               }
//            },{
//                    it -> Log.e("ListRepository ERROR", it.toString())
//            })
//    }

    @SuppressLint("CheckResult")
    fun getStart (symbol: String, token: String){
        db.getAllConstituents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    if (it.count()==0){
                        Log.d("LIST", "List.count = 0")
                        Api.apiClient.getIndicesConstituents(symbol, token)
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe({
                                    Log.d("Insert", it.constituents.size.toString())

                                    for (element in it.constituents)
                                        db.insertConstituents(DataConstituents(element, false))
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(Schedulers.io())
                                                .subscribe({},{})


                                    Log.d("Insert", "insert was")
                                            .also {

                                    db.getAllConstituents()
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe({ data ->
                                                Log.d("Insert", data.size.toString())
                                                dataViewModel.setConstituents(data)
                                            }, { error ->
                                                Log.e("ListRepository ERROR", error.toString())
                                            })
                                }
                                },{
                                    Log.e("ListRepository ERROR", it.toString())
                                })

                    }
                    else {
                        Log.d("LIST", "List.count != 0")
                        dataViewModel.setConstituents(it)
                    }
                           },{
                    Log.e("ListRepository ERROR", it.toString())
                })
    }


    @SuppressLint("CheckResult")
    fun getDataConstituents(){
        db.getAllConstituents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    dataViewModel.setConstituents(it)
                },{
                    it -> Log.e("ListRepository ERROR", it.toString())
                })
    }








}