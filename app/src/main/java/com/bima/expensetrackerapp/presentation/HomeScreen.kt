package com.bima.expensetrackerapp.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bima.expensetrackerapp.presentation.component.BalanceCard
import com.bima.expensetrackerapp.presentation.component.shapes_container.RoundedCornerShapeContainer
import com.bima.expensetrackerapp.presentation.component.tabs.TransactionTabs
import com.bima.expensetrackerapp.viewmodel.AuthViewModel
import com.bima.expensetrackerapp.viewmodel.UserViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    onNavigateToAuth: () -> Unit,
    navController: NavController,
) {
    val userState by userViewModel.userState.collectAsStateWithLifecycle()
    val authState by authViewModel.authState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = MaterialTheme.colorScheme.secondaryContainer
    )
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConstraintLayout {
            val (container, totalBalance, space) = createRefs()
            RoundedCornerShapeContainer(
                modifier = modifier.constrainAs(container) {}
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
            Box(
                modifier = modifier.constrainAs(totalBalance) {
                    top.linkTo(space.bottom, margin = 60.dp)
                    start.linkTo(container.start)
                    end.linkTo(container.end)
                    bottom.linkTo(container.bottom)
                }
            ) {
                BalanceCard()
            }
        }
        Spacer(modifier = modifier.padding(10.dp))
        TransactionTabs(navController = navController)
    }
}