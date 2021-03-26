package test.app.exchange_app_yandex.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import test.app.exchange_app_yandex.R
import test.app.exchange_app_yandex.db.DbConstituents
import io.reactivex.schedulers.Schedulers
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class FavoriteFragment : Fragment() {

    companion object {
        fun newInstance(): FavoriteFragment {
            return FavoriteFragment()
        }
    }

    private val TOKEN: String = "c114bi748v6t4vgvsoj0"
    private val favViewModel by lazy { ViewModelProviders.of(this).get(FavoriteViewModel::class.java)}

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val minflater = inflater.inflate(R.layout.fragment_favorite, container, false)

        val db = DbConstituents.getDatabase(context as AppCompatActivity).movieDao()

        val repFav = FavoriteRepository(db, favViewModel)

        val recyclerView: RecyclerView = minflater.findViewById(R.id.fav_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapterFav = FavoriteAdapter(context as AppCompatActivity, repFav)
        recyclerView.adapter = adapterFav

        repFav.getFavorite()

        favViewModel.getData().observe(this, Observer {
            it?.let {
            adapterFav.setListData(it)
            }
        })

        val editText: EditText = minflater.findViewById(R.id.find_in_fav)

        Observable.create <String> { it->
            editText.addTextChangedListener { data -> it.onNext(data.toString())} }
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                           repFav.findFavElements(it)
                },{
                })

        retainInstance = true

        return minflater
    }
}