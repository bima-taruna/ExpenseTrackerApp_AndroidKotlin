package com.bima.expensetrackerapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.bima.expensetrackerapp.presentation.component.form.CurrencyTextField

@Composable
fun EditDialog(
    modifier: Modifier = Modifier,
    text: MutableState<String>,
    amount:MutableState<Double>,
    onValueChange:()->Unit,
    onDismissRequest:()->Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(16.dp)

            ,
            shape = RoundedCornerShape(16.dp),
        ) {
           CurrencyTextField(
               text = text,
               amount = amount,
               label = "Total Balance",
               onValueChange = { onValueChange() },
               isError = false,
               modifier = modifier.padding(16.dp)
           )
        }
    }
}
