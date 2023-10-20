package com.bima.expensetrackerapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bima.expensetrackerapp.presentation.navigation.Screen
import com.bima.expensetrackerapp.viewmodel.AuthViewModel
import com.bima.expensetrackerapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val sessionState by authViewModel.session.collectAsState()
    val userState by userViewModel.userState.collectAsState()
    LaunchedEffect(sessionState?.user) {
        if (sessionState == null) {
            navController.navigate(Screen.Title.route) {
                popUpTo(Screen.HomeScreen.route) {
                    inclusive = true
                }
            }
        }
    }
    Column() {
        ConstraintLayout {
            val (container,totalBalance, space) = createRefs()
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.30f)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .constrainAs(container) {}
            ) {
                Text(
                    modifier = modifier
                        .padding(top = 16.dp, start = 8.dp),
                    text = "Good Morning ${userState.user?.name}",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            Spacer(modifier = modifier
                .padding(40.dp)
                .constrainAs(space) {
                    top.linkTo(container.top)
                    start.linkTo(container.start)
                    end.linkTo(container.end)
                }
            )
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = modifier
                    .clip(
                        RoundedCornerShape(15.dp)
                    )
                    .fillMaxWidth(0.70f)
                    .fillMaxHeight(0.20f)
                    .background(MaterialTheme.colorScheme.primary)
                    .constrainAs(totalBalance) {
                        top.linkTo(space.bottom, margin = 60.dp)
                        start.linkTo(container.start)
                        end.linkTo(container.end)
                        bottom.linkTo(container.bottom)
                    }
            ) {
                Text(
                    "Total Balance",
                    modifier = modifier.padding(top = 12.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
        Button(onClick = {
            coroutineScope.launch {
                authViewModel.signOut()
            }
        }) {
            Text("Sign Out")
        }
    }
}