package com.bima.expensetrackerapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bima.expensetrackerapp.common.convert
import com.bima.expensetrackerapp.common.getSymbol

import com.bima.expensetrackerapp.viewmodel.BalanceViewModel

@Composable
fun BalanceCard(
    modifier: Modifier = Modifier,
    balanceViewModel: BalanceViewModel = hiltViewModel(),
) {
    val balanceState by balanceViewModel.balanceState.collectAsStateWithLifecycle()
    val totalBalance = balanceState.balance?.totalBalance
    val expense = balanceState.balance?.expense
    val income = balanceState.balance?.income

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

    ) {
        Box {
            ConstraintLayout {
                val (totalBalanceRef, expenseRef, incomeRef, editButtonRef) = createRefs()
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .fillMaxWidth()
                        .constrainAs(totalBalanceRef) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)

                        }) {
                    Text(
                        "Total Balane :",
                        modifier = modifier
                            .padding(top = 12.dp),
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W600
                    )
                    Text(
                        "${getSymbol()} ${totalBalance?.toBigDecimal()}",
                        modifier = modifier
                            .padding(top = 12.dp),
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W600
                    )
                }
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = modifier
                        .size(50.dp)
                        .constrainAs(editButtonRef) {
                            top.linkTo(totalBalanceRef.top)
                            end.linkTo(parent.end)
                        }) {
                    Icon(imageVector = Icons.Filled.Edit, contentDescription = "edit")
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .fillMaxWidth(0.50f)
                        .fillMaxHeight(0.40f)
                        .constrainAs(expenseRef) {
                            top.linkTo(totalBalanceRef.bottom, 8.dp)
                            start.linkTo(parent.start)
                            end.linkTo(incomeRef.start)
                            bottom.linkTo(parent.bottom)
                        }
                ) {
                    Text(
                        text = "Expense",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "${expense?.convert()}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Column(
                    modifier
                        .fillMaxWidth(0.50f)
                        .fillMaxHeight(0.40f)
                        .constrainAs(incomeRef) {
                            top.linkTo(totalBalanceRef.bottom, margin = 8.dp)
                            start.linkTo(expenseRef.end)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Income",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "${income?.convert()}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}




