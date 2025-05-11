package com.example.foodwasting.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisConfig
import co.yml.charts.axis.AxisData
import co.yml.charts.axis.DataCategoryOptions
import co.yml.charts.axis.Gravity
import co.yml.charts.common.model.PlotType
import co.yml.charts.common.model.Point
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarChartType
import co.yml.charts.ui.barchart.models.BarData
import co.yml.charts.ui.barchart.models.BarStyle
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.foodwasting.R
import com.example.foodwasting.model.Recipie
import com.example.foodwasting.ui.theme.backgroundDark
import com.example.foodwasting.ui.theme.bodyFontFamily
import com.example.foodwasting.ui.theme.fontAladin
import com.example.foodwasting.ui.theme.fontEconomica
import com.example.foodwasting.ui.theme.lightBackground
import com.example.foodwasting.ui.theme.onPrimaryLight

@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    var textFealdvaluew by remember {
        mutableStateOf("")
    }
    Row {
        for (i in 1..5) {
            Card(
                modifier = Modifier
                    .size(width = 50.dp, height = 20.dp)
                    .padding(3.dp)
                    .height(35.dp)
                    .clip(shape = RoundedCornerShape(20.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Gray,
                    contentColor = Color.White
                )
            ) {
                Text("tag", modifier = Modifier.padding(5.dp),
                    fontSize = 11.sp,
                    color = Color.White
                    )
            }
        }
    }
    OutlinedTextField(
        shape = RoundedCornerShape(12.dp),
        value = textFealdvaluew,
        label = { Text("Enter Your Name") },
        onValueChange = { textFealdvaluew = it },
        modifier = modifier
            .fillMaxWidth()
            .height(66.dp)

    )
}

@Composable
fun FoodCard(
    text: String,
    list: List<Recipie>,
    color: Color, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .background(color = lightBackground)
            .border(2.dp, color = color, shape = RoundedCornerShape(20.dp)),

        )
    {
        Column(Modifier.background(color = lightBackground)) {
            Text(
                text,
                style = TextStyle(color = color, fontFamily = fontAladin),
                fontSize = 30.sp,
                modifier = Modifier
                    .align(alignment = Alignment.Start)
                    .padding(start = 10.dp, top = 10.dp)

            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxSize()


            ) {
                items(list) { item ->
                    Recepiecard(item)
                }
            }
        }

    }
}


@Composable
private fun Recepiecard(
    recipie: Recipie
) {
    Column(
        modifier = Modifier
            .padding(5.dp)
            .background(Color.White)
    ) {
        Text(
            recipie.title,
            style = TextStyle(color = Color.Green,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = bodyFontFamily
                ),
            modifier = Modifier.padding(bottom = 5.dp)
        )
        Text(recipie.centent, style = TextStyle(fontSize = 15.sp,
            fontFamily = bodyFontFamily
            ))
    }
}


@Composable
fun categories(modifier: Modifier = Modifier) {
    val png = R.drawable.img_apple
    val foodList = listOf(
        png,
        png,
        png,
        png,
        png,
        png,
        png,
        png,
        png,
        png,
        png,
    )
    LazyRow {
        items(foodList) { item ->
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(item),
                contentDescription = "Apple"
            )
        }
    }

}

@Composable
fun FoodGraph(modifier: Modifier = Modifier) {
    val donutChartData = PieChartData(
        slices = listOf(
            PieChartData.Slice("apple", 15f, Color(0xFF5F0A87)),
            PieChartData.Slice("orange", 30f, Color(0xFF20BF55)),
            PieChartData.Slice("khobs", 40f, Color(0xFFEC9F05)),
            PieChartData.Slice("potato", 10f, Color(0xFFF53844))
        ),
        plotType = PlotType.Donut
    )
    val donutChartConfig = PieChartConfig(
        //isAnimationEnable = true,
        strokeWidth = 50f,
        showSliceLabels = true,
        activeSliceAlpha = 1f,
        animationDuration = 600,

    )
    DonutPieChart(
        modifier = modifier,
        donutChartData,
        donutChartConfig,
        onSliceClick = {}
    )

}
   
@Preview
@Composable
fun FoodBarChart(modifier: Modifier = Modifier) {
    val yStepSize = 10
    val maxRange=50

    data class BarEntry(
        val label: String,
        val value: Float
    )

    val barData = listOf(
        BarEntry("tomato", 120.5f),
        BarEntry("potato", 90.0f),
        BarEntry("khobs", 130.0f),
        BarEntry("carrot", 70.5f),
        BarEntry("orange", 160.0f)
    )

// Map barData to BarData for ycharts
    val chartData = barData.mapIndexed { index, entry ->
        BarData(
            point = Point(x = index.toFloat(), y = entry.value),
            color = Color.hsl(index * 60f, 0.7f, 0.5f), // Unique colors
            label = entry.label
        )
    }


    val xAxisData = AxisData
        .Builder()
        .axisStepSize(20.dp)
        .steps(chartData.size - 1)
        .axisOffset(5.dp)
        .axisLabelAngle(20f)
        .labelData { index -> barData[index].label }
        .startDrawPadding(20.dp)
        .axisConfig(AxisConfig(shouldEllipsizeAxisLabel = false))
        .shouldDrawAxisLineTillEnd(true)
        .build()




    val yAxisData = AxisData
        .Builder()
        .steps(yStepSize)
        .labelAndAxisLinePadding(20.dp)
        .labelData { index -> (index * (maxRange / yStepSize)).toString() }
        .axisLineColor(Color.Red)
        .build()

    val barChartDatas = BarChartData(
        chartData = chartData,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        barStyle = BarStyle(barWidth = 10.dp)

    )
    BarChart(modifier = modifier.fillMaxSize(),                                                                                                                                      barChartData = barChartDatas)
}


@Composable
fun FoodLineChart(modifier: Modifier = Modifier) {

    val steps = 10
    val yScale = 20 / steps

    val pointsData: List<Point> =
        listOf(Point(0f, 40f), Point(1f, 90f), Point(2f, 0f), Point(3f, 60f), Point(4f, 10f))

    val xAxisData = AxisData.Builder()
        .axisStepSize(100.dp)
        .steps(pointsData.size - 1)
        .labelData { i -> i.toString() }
        .labelAndAxisLinePadding(15.dp)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(steps)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i -> (i * yScale).toString() }
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(),
                    IntersectionPoint(),
                    SelectionHighlightPoint(),
                    ShadowUnderLine(),
                    SelectionHighlightPopUp()
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(),
        backgroundColor = Color.White
    )

    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        lineChartData = lineChartData
    )
}