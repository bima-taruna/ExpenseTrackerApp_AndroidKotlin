package com.bima.expensetrackerapp.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun Modal(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
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
               content()
            }
        }
    }
}
