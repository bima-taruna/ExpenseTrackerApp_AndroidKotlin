package com.bima.expensetrackerapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.bima.expensetrackerapp.presentation.component.form.CurrencyTextField
import com.bima.expensetrackerapp.viewmodel.state.form.BalanceFormState

@Composable
fun EditDialog(
    modifier: Modifier = Modifier,
    text: MutableState<String>,
    amount: MutableState<Double>,
    onValueChange: () -> Unit,
    onDismissRequest: () -> Unit,
    onSubmit: () -> Unit,
    formState: BalanceFormState,
) {
    val context = LocalContext.current
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                ,
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CurrencyTextField(
                    text = text,
                    amount = amount,
                    label = "Total Balance",
                    onValueChange = { onValueChange() },
                    isError = formState.amountError != null,
                    modifier = modifier
                )
                if (formState.amountError != null) {
                    Text(
                        text = formState.amountError.asString(context) ?: "",
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = { onSubmit() }) {
                        Text(text = "Update")
                    }
                }
            }
        }
    }
}
