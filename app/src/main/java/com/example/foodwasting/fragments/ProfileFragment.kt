package com.example.foodwasting.fragments

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodwasting.R
import com.example.foodwasting.ui.theme.lightBackground


@Preview
@Composable
fun ProfileFragment(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = lightBackground)
            .padding(10.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Spacer(modifier = Modifier.size(50.dp))
        Image(painter = painterResource(R.drawable.img_profilephoto),
            contentDescription = "Profile Photo" ,
            alignment = Alignment.Center)

        Text("Nasro ghellale" , modifier = Modifier.padding(top = 10.dp), fontSize = 30.sp)
    }
}