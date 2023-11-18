package com.bima.expensetrackerapp.presentation.component.shapes_container

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun RoundedCornerShapeContainer(
    modifier:Modifier = Modifier,
    content : @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.30f)
            .clip(shape = RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        content()
    }
}