package com.bima.expensetrackerapp.presentation.component.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bima.expensetrackerapp.common.convert
import com.bima.expensetrackerapp.domain.model.Transactions
import com.bima.expensetrackerapp.presentation.component.shapes_container.SmallCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionCard(
    state: Transactions,
    modifier: Modifier = Modifier,
    isIncome:Boolean,
    navigateToDetail:()->Unit
) {
       SmallCard(onClick = { navigateToDetail() }) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                    Column {
                        Text(text = "${state.name}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(text = "${state.date}", style = MaterialTheme.typography.titleSmall)
                    }
                    Text(
                        text = "Rp ${state.amount?.convert()}",
                        style= MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = if(isIncome) Color.Green else Color.Red
                    )
            }
        }
       }

