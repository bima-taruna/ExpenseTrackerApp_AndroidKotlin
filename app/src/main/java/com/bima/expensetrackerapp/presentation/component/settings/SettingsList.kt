package com.bima.expensetrackerapp.presentation.component.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bima.expensetrackerapp.presentation.navigation.Screen

@Composable
fun SettingsList(
    modifier: Modifier = Modifier,
    list: List<String>,
    navController: NavController
) {
    LazyColumn() {
        items(list) {
            SettingsCard(name = it, modifier = modifier.fillMaxWidth(), navigate = {navController.navigate(
                returnTheSettingsRoute(it))})
            Divider(modifier = modifier.padding(horizontal = 8.dp))
        }
    }
}

fun returnTheSettingsRoute(name:String): String {
    if (name == "Category") {
        return Screen.CategorySettingScreen.route
    }
    return ""
}

//@Preview(showBackground = true, widthDp = 320, heightDp = 320)
//@Composable
//fun SettingsListPreview() {
//    SettingsList(list = listOf("Dark theme", "Currency", "Category"))
//}