package test.app.exchange_app_yandex.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import test.app.exchange_app_yandex.R
import test.app.exchange_app_yandex.db.DbConstituents
import test.app.exchange_app_yandex.list.ListViewModel


class FavoriteFragment : Fragment() {

    companion object {
        fun newInstance(): FavoriteFragment {
            return FavoriteFragment()
        }
    }

    private val TOKEN: String = "c114bi748v6t4vgvsoj0"

    private val dataViewModel by lazy { ViewModelProviders.of(this).get(ListViewModel::class.java)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val minflater = inflater.inflate(R.layout.fragment_favorite, container, false)

        val recyclerView = minflater.findViewById<RecyclerView>(R.id.list_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapterFav = FavoriteFragment()

        val db = DbConstituents.getDatabase(context as AppCompatActivity).movieDao()

        val rep = FavoriteRepository(db)

        return minflater
    }

}