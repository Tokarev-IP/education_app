package test.app.exchange_app_yandex.chart

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import test.app.exchange_app_yandex.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChartFragment(private val symbol: String) : Fragment() {

    private val TOKEN: String = "c114bi748v6t4vgvsoj0"
    private val chartViewModel by lazy { ViewModelProviders.of(this).get(ChartViewModel::class.java)}

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val minflater = inflater.inflate(R.layout.fragment_chart, container, false)

        val prbar: ProgressBar = minflater.findViewById(R.id.progress_bar_chart)
        prbar.visibility = View.VISIBLE

        val chartRep = ChartRepository(chartViewModel)
        val currentDate = Date().time/1000

        val monthButton: Button = minflater.findViewById(R.id.month_button)
        val yearButton: Button = minflater.findViewById(R.id.year_button)
        yearButton.setTextColor(Color.BLACK)
        monthButton.setTextColor(Color.WHITE)

        monthButton.setOnClickListener {
            prbar.visibility = View.VISIBLE
            monthButton.setBackgroundColor(resources.getColor(R.color.teal_200))
            yearButton.setBackgroundColor(Color.WHITE)
            yearButton.setTextColor(Color.BLACK)
            monthButton.setTextColor(Color.WHITE)
            chartRep.getStockCandle(symbol, "D", currentDate - 15000000, currentDate, TOKEN)
        }

        yearButton.setOnClickListener {
            prbar.visibility = View.VISIBLE
            monthButton.setBackgroundColor(Color.WHITE)
            monthButton.setTextColor(Color.BLACK)
            yearButton.setBackgroundColor(resources.getColor(R.color.teal_200))
            yearButton.setTextColor(Color.WHITE)
            chartRep.getStockCandle(symbol, "W", currentDate - 150000000, currentDate, TOKEN)
        }

        yearButton.setBackgroundColor(Color.WHITE)
        yearButton.setTextColor(Color.BLACK)
        chartRep.getStockCandle(symbol, "D", currentDate - 15000000, currentDate, TOKEN)

        chartViewModel.getData().observe(this, androidx.lifecycle.Observer {
            it?.let {

                val lineChart: LineChart = minflater.findViewById(R.id.chart)
                lineChart.setScaleEnabled(false)
                lineChart.maxHighlightDistance = 1000F
                lineChart.description.text = "$symbol price chart "
                lineChart.description.textSize = 15F
                lineChart.setDrawMarkers(true)
                lineChart.marker = MyMarkerView(context as AppCompatActivity, R.layout.merker_view)
//                lineChart.description.textColor = resources.getColor(R.color.teal_200)
//                lineChart.description.isEnabled = false

                val dataSet: LineDataSet = LineDataSet(it, "$symbol, USD")
                dataSet.setDrawCircles(false)
                dataSet.color = Color.BLACK
                dataSet.lineWidth = 2F

                val dataLine: LineData = LineData(dataSet)
                dataLine.setValueTextColor(Color.RED)
                dataLine.setDrawValues(false)

                lineChart.data = dataLine
                lineChart.setNoDataText("WAITING")

                val xChart = lineChart.xAxis
                xChart.textColor = Color.BLACK
                xChart.gridColor = Color.BLACK
                xChart.axisLineColor = Color.BLACK
                xChart.setDrawGridLines(false)

                if (currentDate.toFloat() - it[0].x > 50000000F) {
                    xChart.valueFormatter = YearFormatter()
                    xChart.labelCount = 5
                }
                else {
                    xChart.valueFormatter = MonthFormatter()
                    xChart.labelCount = 4
                }

                val yChartLeft = lineChart.getAxis(YAxis.AxisDependency.LEFT)
                yChartLeft.labelCount = 10
                yChartLeft.textColor = Color.BLACK
                yChartLeft.gridColor = Color.BLACK
                yChartLeft.axisLineColor = Color.BLACK
                yChartLeft.setDrawGridLines(false)

                val yChartRight = lineChart.getAxis(YAxis.AxisDependency.RIGHT)
                yChartRight.labelCount = 10
                yChartRight.textColor = Color.BLACK
                yChartRight.gridColor = Color.BLACK
                yChartRight.axisLineColor = Color.BLACK
                yChartRight.setDrawGridLines(false)

                lineChart.invalidate()

                prbar.visibility = View.INVISIBLE
            }
        })

        retainInstance = true

        return minflater
    }

}