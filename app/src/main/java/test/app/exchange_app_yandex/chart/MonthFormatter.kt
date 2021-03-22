package test.app.exchange_app_yandex.chart

import android.annotation.SuppressLint
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat

class MonthFormatter: ValueFormatter() {

    @SuppressLint("SimpleDateFormat")
    override fun getFormattedValue(value: Float): String {
        val dateLong = value.toLong()*1000
        val formatDate = SimpleDateFormat("dd/M/yyyy")
        return formatDate.format(dateLong)
    }
}