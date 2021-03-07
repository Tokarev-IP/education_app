package test.app.exchange_app_yandex.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import test.app.exchange_app_yandex.R

class ListFragment : Fragment() {

    companion object {
        fun newInstance(): ListFragment {
            return ListFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val minflater = inflater.inflate(R.layout.fragment_list, container, false)

//        val recyclerView: RecyclerView = minflater.findViewById(R.id.list_recycler_view)
//        recyclerView.layoutManager = LinearLayoutManager(context)
//        val adapter = ListAdapter()
//        recyclerView.adapter = adapter

        return minflater
    }
}