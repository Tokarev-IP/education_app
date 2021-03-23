package test.app.exchange_app_yandex.favorite

import android.annotation.SuppressLint
import android.util.Log
import test.app.exchange_app_yandex.db.DaoConstituents
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.Single
import test.app.exchange_app_yandex.db.DataConstituents

class FavoriteRepository(private val db: DaoConstituents, private val favViewModel: FavoriteViewModel) {

    @SuppressLint("CheckResult")
    fun getFavorite(){
        db.getFavoriteAllConstituents()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                            fromListToArray(it)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe({ array->
                                        favViewModel.setData(array)
                                    },{})
                },{
                    Log.e("FAV REP ERROR", it.toString())
                })
    }

    fun fromListToArray(list: List<DataConstituents>): Single<ArrayList<DataConstituents>> {
        val array: ArrayList<DataConstituents> = ArrayList()
        for (element in list)
            array.add(element)
        return Single.create {
            it.onSuccess(array)
        }
    }

    @SuppressLint("CheckResult")
    fun findFavElements(editData: String){
        db.findConstituentFavorite(editData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    fromListToArray(it)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ array->
                                favViewModel.setData(array)
                            },{})
                },{
                    Log.e("FAV REP ERROR", it.toString())
                })
    }

}