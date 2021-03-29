package test.app.exchange_app_yandex.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProviders
import androidx.paging.LivePagedListBuilder
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import test.app.exchange_app_yandex.R
import test.app.exchange_app_yandex.db.DbConstituents
import java.util.concurrent.TimeUnit

class ListFragment : Fragment() {

    companion object {
        fun newInstance(): ListFragment {
            return ListFragment()
        }
    }

    private var FIND: String = "null"
    private val SYMBOL :String = "^GSPC"
    private val TOKEN: String = "c114bi748v6t4vgvsoj0"

    private val dataViewModel by lazy { ViewModelProviders.of(this).get(ListViewModel::class.java)}

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val minflater = inflater.inflate(R.layout.fragment_list, container, false)

        val db = DbConstituents.getDatabase(context as AppCompatActivity).movieDao()

        val repList = ListRepository(db, dataViewModel)

        val recyclerView: RecyclerView = minflater.findViewById(R.id.list_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(context, resources.getInteger(R.integer.col_count))
        val adapter = ListAdapter(context as AppCompatActivity, repList)
        recyclerView.adapter = adapter

        if (savedInstanceState != null) {
            FIND = savedInstanceState.getString("FIND").toString()
        }
        else
        {
            repList.getFactoryData(SYMBOL, TOKEN)
        }

        dataViewModel.getData().observe(this) {
            adapter.submitList(it)
        }

        val editText: EditText = minflater.findViewById(R.id.find_in_list)

        Observable.create <String> { it->
            editText.addTextChangedListener { data -> it.onNext(data.toString())} }
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    FIND = it
                    val data = db.findConstituent(it)
                    repList.setViewModel(data)
                    val config = dataViewModel.getConfig()
                    val liveData = LivePagedListBuilder(data, config).build()
                    liveData.observe(this){ dt->
                        adapter.submitList(dt)}

                },{
                    Log.e("EDITTEXT ERROR", it.toString())
                })

        retainInstance = true

        return minflater
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (FIND != "null") outState.putString("EDIT", FIND)
    }

}