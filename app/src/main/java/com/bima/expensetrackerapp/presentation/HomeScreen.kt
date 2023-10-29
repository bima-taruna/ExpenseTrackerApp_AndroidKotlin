package com.bima.expensetrackerapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bima.expensetrackerapp.presentation.navigation.Screen
import com.bima.expensetrackerapp.viewmodel.AuthViewModel
import com.bima.expensetrackerapp.viewmodel.BalanceViewModel
import com.bima.expensetrackerapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    balanceViewModel: BalanceViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val sessionState by authViewModel.session.collectAsStateWithLifecycle()
    val userState by userViewModel.userState.collectAsStateWithLifecycle()
    val balanceState by balanceViewModel.balanceState.collectAsStateWithLifecycle()
    LaunchedEffect(sessionState?.user) {
        if (sessionState == null) {
            navController.navigate(Screen.Title.route) {
                popUpTo(Screen.HomeScreen.route) {
                    inclusive = true
                }
            }
        }
    }
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConstraintLayout {
            val (container, totalBalance, space) = createRefs()
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.30f)
                    .clip(shape = RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .constrainAs(container) {}
            ) {
                Text(
                    modifier = modifier
                        .padding(top = 16.dp, start = 16.dp),
                    text = "Good Morning ${userState.user?.name}!",
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
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
            ElevatedCard(
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                modifier = modifier
                    .fillMaxWidth(0.90f)
                    .fillMaxHeight(0.30f)
                    .constrainAs(totalBalance) {
                        top.linkTo(space.bottom, margin = 60.dp)
                        start.linkTo(container.start)
                        end.linkTo(container.end)
                        bottom.linkTo(container.bottom)
                    }
            ) {
                Text(
                    "Total Balance :",
                    modifier = modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W600
                )
                Text(
                    "${balanceState.balance?.totalBalance}",
                    modifier = modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W600
                )
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Income",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "${balanceState.balance?.income}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Expense",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "${balanceState.balance?.expense}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
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