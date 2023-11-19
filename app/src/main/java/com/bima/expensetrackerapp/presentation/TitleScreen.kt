package com.bima.expensetrackerapp.presentation

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bima.expensetrackerapp.R
import com.bima.expensetrackerapp.presentation.component.fadingEdge
import com.bima.expensetrackerapp.presentation.navigation.AuthScreen
import com.bima.expensetrackerapp.presentation.navigation.Graph
import com.bima.expensetrackerapp.viewmodel.AuthViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun TitleScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val sessionState by viewModel.session.collectAsState()
    val context = LocalContext.current
    val topBottomFade = Brush.verticalGradient( 0.7f to Color.Red, 0.9f to Color.Transparent)
    LaunchedEffect(sessionState?.user) {
        if (sessionState?.user != null) {
            navController.navigate(Graph.MAIN) {
                popUpTo(Graph.AUTH) {
                    inclusive = true
                }
            }
            Toast.makeText(context, "Login Berhasil", Toast.LENGTH_LONG).show()
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .fillMaxHeight(0.6f)
                .fillMaxWidth()
                .fadingEdge(topBottomFade)

        ) {
            GlideImage(
                imageModel = { R.drawable.josh_appel_netpasr_bmq_unsplash },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.FillHeight,
                    alignment = Alignment.Center,
                )
            )
        }
        Spacer(modifier = modifier.height(20.dp))
        Text(
            text = "Expense Tracker",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = modifier.height(20.dp))
        Column(modifier = modifier.padding(horizontal = 16.dp)) {
            Button(
                onClick = { navController.navigate(AuthScreen.Login.route) }, modifier = modifier
                    .fillMaxWidth()
                    .size(50.dp), shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Login", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = modifier.height(10.dp))
            OutlinedButton(
                onClick = { /*TODO*/ }, modifier = modifier
                    .fillMaxWidth()
                    .size(50.dp), shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Sign Up", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }

    }
}
