package com.example.foodwasting.fragments

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodwasting.R
import com.example.foodwasting.ui.FoodBarChart
import com.example.foodwasting.ui.FoodGraph
import com.example.foodwasting.ui.FoodLineChart
import com.example.foodwasting.ui.theme.lightBackground
import com.example.foodwasting.ui.theme.lightgreen


@Preview(showSystemUi = true)
@Composable
fun ProfileFragment() {
    val scrollState =
        rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(color = lightBackground)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(20.dp))

        Image(
            painter = painterResource(R.drawable.img_profilephoto),
            contentDescription = "Profile Photo",
            alignment = Alignment.Center,
            modifier = Modifier
                .size(150.dp)
                .padding(10.dp)
        )

        Text("food waster", modifier = Modifier.padding(top = 10.dp), fontSize = 30.sp)

        Row {
            repeat(3) {
                Card(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(10.dp)
                        .size(100.dp, 70.dp)
                        .border(1.dp, color = lightgreen, shape = RoundedCornerShape(10.dp)),
                    content = {
                        Text(
                            "20",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp),
                            style = TextStyle(
                                fontSize = 30.sp,
                                textAlign = TextAlign.Center
                            )
                        )
                        Text(
                            "Food Saved",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 5.dp),
                            style = TextStyle(textAlign = TextAlign.Center)
                        )
                    }
                )
            }
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Box(
                modifier = Modifier
                    .weight(0.9f)
                    .height(200.dp)
                    .fillMaxSize()
                    .background(lightBackground)
                    .border(2.dp, color = lightgreen, shape = RoundedCornerShape(20.dp))
                    .padding(2.dp),
            ) {
                FoodBarChart(Modifier.padding(2.dp))
            }

            Spacer(Modifier.width(10.dp))

            Box(
                modifier = Modifier
                    .weight(0.9f)
                    .height(200.dp)
                    .background(lightBackground)
                    .border(2.dp, color = lightgreen, shape = RoundedCornerShape(20.dp))
                    .padding(2.dp),
            ) {
                FoodGraph(Modifier.padding(5.dp))
            }
        }

        Box(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(2.dp, color = lightgreen, shape = RoundedCornerShape(20.dp))
        ) {
            FoodLineChart()
        }
    }
}


