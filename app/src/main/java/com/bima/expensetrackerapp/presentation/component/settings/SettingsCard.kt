@file:OptIn(ExperimentalMaterial3Api::class)

package com.bima.expensetrackerapp.presentation.component.settings

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsCard(
    modifier: Modifier = Modifier,
    name:String
) {
    Card(
        onClick = { /*TODO*/ },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Row(modifier = modifier
            .padding(12.dp)
            .fillMaxWidth()) {
            Text(name)
        }
    }
}

@Preview
@Composable
fun SettingsCardPreview() {
    SettingsCard(name = "category")
}