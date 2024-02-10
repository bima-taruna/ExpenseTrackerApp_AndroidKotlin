package com.bima.expensetrackerapp.presentation.component.income

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bima.expensetrackerapp.viewmodel.CategoryViewModel
import com.bima.expensetrackerapp.viewmodel.income.IncomeViewModel
import com.bima.expensetrackerapp.viewmodel.income.UpdateIncomeViewModel

@Composable
fun UpdateIncome(
    id: String,
    navController: NavController,
    incomeViewModel: IncomeViewModel = hiltViewModel(),
    updateIncomeViewModel: UpdateIncomeViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel(),
) {

}