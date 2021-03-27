package test.app.exchange_app_yandex.chart

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import test.app.exchange_app_yandex.R
import java.text.SimpleDateFormat

class MyMarkerView(context: Context, layoutResource: Int) :
        MarkerView(context, layoutResource) {

    private val window: TextView = findViewById(R.id.marker_data_window)

    @SuppressLint("SetTextI18n")
    override fun refreshContent(e: Entry, highlight: Highlight?) {

        window.text = e.y.toString().format("%.2f") +
                "\n" + dateToString(e.x)
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height-100).toFloat())
    }

    @SuppressLint("SimpleDateFormat")
    fun dateToString(value: Float): String {
        val dateLong = value.toLong() * 1000
        val formatDate = SimpleDateFormat("dd/M/yyyy")
        return formatDate.format(dateLong)
    }

}