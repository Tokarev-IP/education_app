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

class ChartFragment : Fragment() {

    companion object {
        fun newInstance(): ChartFragment {
            return ChartFragment()
        }
    }

    private val TOKEN: String = "c114bi748v6t4vgvsoj0"
    private val chartViewModel by lazy { ViewModelProviders.of(this).get(ChartViewModel::class.java)}

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val minflater = inflater.inflate(R.layout.fragment_chart, container, false)

        val chartRep = ChartRepository(chartViewModel)
        val currentDate = Date().time/1000

        val monthButton: Button = minflater.findViewById(R.id.month_button)
        val yearButton: Button = minflater.findViewById(R.id.year_button)

        monthButton.setOnClickListener {
            chartRep.getStockCandle("AAPL", "D", currentDate-15000000, currentDate, TOKEN)
        }

        yearButton.setOnClickListener {
            chartRep.getStockCandle("AAPL", "W", currentDate-90000000, currentDate, TOKEN)
        }

        chartRep.getStockCandle("AAPL", "D", currentDate-15000000, currentDate, TOKEN)



        chartViewModel.getData().observe(this, androidx.lifecycle.Observer {
            it?.let {

                val lineChart: LineChart = minflater.findViewById(R.id.chart)
                lineChart.setScaleEnabled(false)
                lineChart.maxHighlightDistance = 1000F

//        val array: ArrayList<Entry> = ArrayList()
//        array.add(Entry(1416358955F,15F))
//        array.add(Entry(1516358955F,10F))
//        array.add(Entry(1616358955F,25F))
//        array.add(Entry(161616354733F,34F))
//        array.add(Entry(161616354743F,22F))
//        array.add(Entry(161616354753F,35F))
//        array.add(Entry(161616354758F,20F))
//        array.add(Entry(161616354763F,45F))
//        array.add(Entry(161616354768F,40F))
//        array.add(Entry(161616354773F,23F))
//        array.add(Entry(161616354778F,18F))
//        array.add(Entry(161616354783F,33F))
//        array.add(Entry(161616354788F,18F))

                val dataSet: LineDataSet = LineDataSet(it, "AAPL, USD")
                dataSet.setDrawCircles(false)
//                dataSet.setDrawFilled(true)
                dataSet.color = Color.BLACK
                dataSet.lineWidth = 2F
//                dataSet.fillColor = resources.getColor(R.color.teal_300)

                val dataLine: LineData = LineData(dataSet)
                dataLine.setValueTextColor(Color.RED)
                dataLine.setDrawValues(false)

                lineChart.data = dataLine
                lineChart.setNoDataText("WAITING")

                val description = lineChart.description
                description.isEnabled = false

                val xChart = lineChart.xAxis
                xChart.textColor = Color.BLACK
                xChart.gridColor = Color.BLACK
                xChart.axisLineColor = Color.BLACK
                xChart.setDrawGridLines(false)
//        xChart.setDrawLabels(false)
//        xChart.axisMinimum = 0F
                xChart.labelCount = 2
                xChart.valueFormatter = MyValueFormatter()

                val yChartLeft = lineChart.getAxis(YAxis.AxisDependency.LEFT)
                yChartLeft.labelCount = 10
                yChartLeft.textColor = Color.BLACK
                yChartLeft.gridColor = Color.BLACK
                yChartLeft.axisLineColor = Color.BLACK
                yChartLeft.setDrawGridLines(false)
//        yChartLeft.setDrawLabels(false)
//        val limitLine: LimitLine = LimitLine(27F ,"Current price")
//        limitLine.lineColor = Color.RED
//        yChartLeft.addLimitLine(limitLine)

                val yChartRight = lineChart.getAxis(YAxis.AxisDependency.RIGHT)
                yChartRight.labelCount = 10
                yChartRight.textColor = Color.BLACK
                yChartRight.gridColor = Color.BLACK
                yChartRight.axisLineColor = Color.BLACK
                yChartRight.setDrawGridLines(false)

                lineChart.invalidate()
            }
        })



        return minflater
    }

}