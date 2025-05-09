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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodwasting.R
import com.example.foodwasting.model.Recipie
import com.example.foodwasting.ui.theme.backgroundDark
import com.example.foodwasting.ui.theme.fontAladin
import com.example.foodwasting.ui.theme.fontEconomica
import com.example.foodwasting.ui.theme.lightBackground
import com.example.foodwasting.ui.theme.onPrimaryLight

@Composable
fun SearchBar(modifier: Modifier = Modifier ) {
    var textFealdvaluew by remember {
        mutableStateOf("")
    }
    Row {
        for (i in 1..5){
        Card(
            modifier = Modifier
                .size(width = 50.dp, height = 20.dp)
                .padding(3.dp)
                .clip(shape = RoundedCornerShape(20.dp))
            ,
            colors = CardDefaults.cardColors(
                containerColor = Color.Gray,
                contentColor = Color.White
            )
        ){
            Text("tag", modifier = Modifier.padding(5.dp))
        }
        }
    }
    OutlinedTextField(
        shape = RoundedCornerShape(20.dp),
        value = textFealdvaluew,
        label = { Text("Enter Your Name", modifier = Modifier.fillMaxWidth()) },
        onValueChange = { textFealdvaluew = it },
        modifier = modifier)
}

@Composable
fun FoodCard(text: String,
             list:List<Recipie>,
             color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .background(color = lightBackground)
            .border(2.dp, color = color , shape = RoundedCornerShape(20.dp))
        ,

    )
    {
            Column (Modifier.background(color = lightBackground)){
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
    Column (modifier = Modifier
        .padding(5.dp)
        .background(Color.White)){
        Text(recipie.title , style = TextStyle(color = Color.Green , fontSize = 20.sp ) , modifier = Modifier.padding(bottom = 5.dp))
        Text(recipie.centent , style = TextStyle(fontSize = 15.sp , fontStyle = FontStyle.Italic))
    }
}


@Composable
fun categories(modifier: Modifier = Modifier ) {
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
        items(foodList){
            item ->
            Image(modifier= Modifier.size(100.dp),painter = painterResource(item),contentDescription = "Apple")
        }
    }

}

