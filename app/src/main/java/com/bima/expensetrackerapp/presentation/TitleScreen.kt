package com.bima.expensetrackerapp.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bima.expensetrackerapp.R
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import java.time.format.TextStyle

@Composable
fun TitleScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier.fillMaxHeight(0.5f)
        ) {
            GlideImage(
                imageModel = { R.drawable.pngtreecartoon_money_bag_gold_coin_6140119 },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.Center,
                ),
                modifier = modifier.size(300.dp)
            )
        }
        Spacer(modifier = modifier.height(20.dp))
        Text(text = "Expense Tracker", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = modifier.height(20.dp))
        Button(onClick = { /*TODO*/ }, modifier = modifier
            .fillMaxWidth()
            .size(50.dp), shape = RoundedCornerShape(12.dp)) {
            Text(text = "Login", fontSize = 16.sp)
        }
        Spacer(modifier =  modifier.height(8.dp))
        OutlinedButton(onClick = { /*TODO*/ }, modifier = modifier
            .fillMaxWidth()
            .size(50.dp), shape = RoundedCornerShape(12.dp)) {
            Text(text = "Sign Up", fontSize = 16.sp)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun TitleScreenPreview() {
    TitleScreen()
}
