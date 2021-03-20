package test.app.exchange_app_yandex.news

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
import test.app.exchange_app_yandex.favorite.FavoriteAdapter
import test.app.exchange_app_yandex.favorite.FavoriteFragment
import test.app.exchange_app_yandex.favorite.FavoriteRepository
import test.app.exchange_app_yandex.favorite.FavoriteViewModel

class NewsFragment : Fragment() {

    companion object {
        fun newInstance(): NewsFragment {
            return NewsFragment()
        }
    }

    //general, forex, crypto, merger
    private val TOKEN: String = "c114bi748v6t4vgvsoj0"

    private val newsViewModel by lazy {ViewModelProviders.of(this).get(NewsViewModel::class.java)}

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val minflater = inflater.inflate(R.layout.fragment_news, container, false)

        val newsRep = NewsRepository(newsViewModel)

        val recyclerView: RecyclerView = minflater.findViewById(R.id.news_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapterNews = NewsAdapter()
        recyclerView.adapter = adapterNews

        newsRep.getNews("general", TOKEN)

        newsViewModel.getData().observe(this, Observer {
            it?.let {
                adapterNews.setData(it)
            }
        })

        return minflater
    }

}