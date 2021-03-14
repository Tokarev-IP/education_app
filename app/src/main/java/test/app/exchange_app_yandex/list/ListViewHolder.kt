package test.app.exchange_app_yandex.list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import test.app.exchange_app_yandex.R

class ListViewHolder(v: View): RecyclerView.ViewHolder(v) {

    val name: TextView = v.findViewById(R.id.company_name_textView)
    val symbol: TextView = v.findViewById(R.id.company_symbol_textView)
    val price: TextView = v.findViewById(R.id.company_price_textView)
    val delta: TextView = v.findViewById(R.id.company_delta_price_textView)
    val logo: ImageView = v.findViewById(R.id.company_logo_image)
}