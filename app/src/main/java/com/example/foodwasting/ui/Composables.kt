package com.example.foodwasting.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisConfig
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.PlotType
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
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
import com.example.foodwasting.model.Recipe
import com.example.foodwasting.ui.theme.fontAladin
import com.example.foodwasting.ui.theme.lightBackground




@Composable
fun FoodCard(
    text: String,
    list: List<Recipe>,
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
                items(list) {  recipe ->
                    // ⭐ FIX: Calling our new, beautiful RecipeCard ⭐
                    RecipeCard(recipe = recipe)
                }
            }
        }

    }
}
@Composable
private fun RecipeCard(recipe: Recipe) {
    Card(
        modifier = Modifier
            .width(160.dp) // A good width for a card in a LazyRow
            .height(180.dp), // A good height to fit content
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White) // Or use a theme color like MaterialTheme.colorScheme.surface
                .padding(12.dp)
        ) {
            // Display the recipe title
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.titleMedium, // Use theme typography
                fontWeight = FontWeight.Bold,
                maxLines = 2, // Prevent long titles from taking too much space
                overflow = TextOverflow.Ellipsis // Add '...' if title is too long
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Display the recipe description
            Text(
                // ⭐ FIX: Using the correct 'description' field
                text = recipe.description,
                style = MaterialTheme.typography.bodySmall, // Use theme typography
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

/*
@Composable
private fun Recepiecard(
    recipie: Recipe = Recipie("ff","qdqsds")
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
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
*/

@Composable
fun Categories() {

    val foodList = listOf(
        R.drawable.img_apple,
        R.drawable.img_rottenappel,
        R.drawable.img_freshbanana,
        R.drawable.img_rottenbanana,
        R.drawable.img_freshcapsicum,
        R.drawable.img_freshbotato,
        R.drawable.img_rottenbotato
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
fun FoodLineChart() {

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