package com.bima.expensetrackerapp.presentation.component.transaction

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bima.expensetrackerapp.presentation.component.shapes_container.RoundedCornerShapeContainer


@ExperimentalMaterial3Api
@Composable
fun TransactionScaffold(
    modifier: Modifier = Modifier,
    title: String,
    hasAction: Boolean,
    delete: () -> Unit = {},
    goToEdit: () -> Unit = {},
    backNavigation: () -> Unit,
    content: @Composable () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        backNavigation()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                actions = {
                    if (hasAction) {
                        IconButton(onClick = {
                            goToEdit()
                        }) {
                            Icon(imageVector = Icons.Filled.Edit, contentDescription = "edit")
                        }
                        IconButton(onClick = {
                            delete()
                        }) {
                            Icon(imageVector = Icons.Filled.Delete, contentDescription = "delete")
                        }
                    }
                },
                title = { Text(text = title, fontWeight = FontWeight.SemiBold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )
        },
        content = { paddingValues ->
            Box(
                modifier = modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                ConstraintLayout {
                    val (container, form) = createRefs()
                    RoundedCornerShapeContainer(
                        modifier = modifier.constrainAs(container) {}
                    ) {}
                    Card(
                        modifier = modifier
                            .fillMaxSize(if (hasAction) 1f else 0.90f)
                            .constrainAs(form) {
                                top.linkTo(container.top, margin = 16.dp)
                                start.linkTo(container.start)
                                end.linkTo(container.end)
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.onSecondary
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        ),
                    ) {
                        content()
                    }
                }
            }
        }
    )
}