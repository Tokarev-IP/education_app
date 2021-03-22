package test.app.exchange_app_yandex.list

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

class ListFragment : Fragment() {

    companion object {
        fun newInstance(): ListFragment {
            return ListFragment()
        }
    }

    private val SYMBOL :String = "^GSPC"
    private val TOKEN: String = "c114bi748v6t4vgvsoj0"

    private val dataViewModel by lazy { ViewModelProviders.of(this).get(ListViewModel::class.java)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val minflater = inflater.inflate(R.layout.fragment_list, container, false)

        val db = DbConstituents.getDatabase(context as AppCompatActivity).movieDao()

        val rep = ListRepository(db, dataViewModel)

        val recyclerView: RecyclerView = minflater.findViewById(R.id.list_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = ListAdapter(db, context as AppCompatActivity)
        recyclerView.adapter = adapter

        rep.getFactoryData(SYMBOL, TOKEN)

        dataViewModel.getData().observe(this) {
            it?.let {
                adapter.submitList(it)
            }
        }

        retainInstance = true

        return minflater
    }

}