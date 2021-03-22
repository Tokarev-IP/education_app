package test.app.exchange_app_yandex.chart

import android.annotation.SuppressLint
import android.util.Log
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*


class MyValueFormatter: ValueFormatter() {

    @SuppressLint("SimpleDateFormat")
    override fun getFormattedValue(value: Float): String {
        val dateLong = value.toLong()*1000
        val formatDate = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        return formatDate.format(dateLong)
    }
}