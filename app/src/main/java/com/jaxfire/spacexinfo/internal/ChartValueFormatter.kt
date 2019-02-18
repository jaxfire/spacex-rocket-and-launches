package com.jaxfire.spacexinfo.internal

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.utils.ViewPortHandler
import java.text.DecimalFormat


class ChartValueFormatter
    : IAxisValueFormatter, IValueFormatter {

    // format values to 1 decimal digit
    private val mFormat: DecimalFormat = DecimalFormat("#")

    override fun getFormattedValue(value: Float, axis: AxisBase): String {
        // "value" represents the position of the label on the axis (x or y)
        val formattedStr = mFormat.format(value)
        return if (formattedStr.length >= 2) "'${formattedStr.substring(formattedStr.length - 2)}" else formattedStr
    }

    override fun getFormattedValue(value: Float, entry: Entry?, dataSetIndex: Int, viewPortHandler: ViewPortHandler?
    ): String {
        return mFormat.format(value)
    }
}