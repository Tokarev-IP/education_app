package test.app.exchange_app_yandex.favorite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import test.app.exchange_app_yandex.R
import test.app.exchange_app_yandex.db.DbConstituents


class FavoriteFragment : Fragment() {

    companion object {
        fun newInstance(): FavoriteFragment {
            return FavoriteFragment()
        }
    }

    private val TOKEN: String = "c114bi748v6t4vgvsoj0"

    private val favViewModel by lazy { ViewModelProviders.of(this).get(FavoriteViewModel::class.java)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val minflater = inflater.inflate(R.layout.fragment_favorite, container, false)

        val db = DbConstituents.getDatabase(context as AppCompatActivity).movieDao()

        val repFav = FavoriteRepository(db, favViewModel)

        val recyclerView: RecyclerView = minflater.findViewById(R.id.fav_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapterFav = FavoriteAdapter(db, repFav)
        recyclerView.adapter = adapterFav

        repFav.getFavorite()

        favViewModel.getData().observe(this, Observer {
            it?.let {
            adapterFav.setListData(it)
            }
        })
        return minflater
    }

}