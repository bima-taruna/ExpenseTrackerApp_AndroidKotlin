package com.bima.expensetrackerapp.presentation.component.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bima.expensetrackerapp.domain.model.Transaction
import com.bima.expensetrackerapp.presentation.component.RowColumn
import com.bima.expensetrackerapp.viewmodel.state.transaction.TransactionState

@Composable
fun TransactionDetail(
    modifier: Modifier = Modifier,
    state: TransactionState,
    isIncome: Boolean,
) {
    Column(
        modifier
            .padding(32.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = state.transaction?.name.toString(),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = modifier.padding(8.dp))
        Card{
            Text(
                text = if (isIncome) "Income" else "Expense",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold,
                color = if (isIncome) Color.Green else Color.Red,
                modifier = modifier.padding(6.dp)
            )
        }
        Spacer(modifier = modifier.padding(20.dp))
        Column(
            modifier = modifier
                .fillMaxSize(0.90f)
                .verticalScroll(
                    rememberScrollState()
                ),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = modifier.fillMaxSize()
            ) {
                Text(text = "Transaction Detail", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
            RowColumn(label = "Status", value = if (isIncome) "Income" else "Expense", color = if (isIncome) Color.Green else Color.Red)
            RowColumn(label = "Name", value = state.transaction?.name.toString(), color = null)
            RowColumn(label = "Date", value = state.transaction?.date.toString(), color = null)
            RowColumn(label = "Amount", value = state.transaction?.amount.toString(), color = null)
            Divider(
                thickness = 1.dp
            )
            Text(text = "Description", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            if (state.transaction?.description != null) {
                Text(text = state.transaction?.description)
            } else {
                Text(text = "no description")
            }
        }

    }
}