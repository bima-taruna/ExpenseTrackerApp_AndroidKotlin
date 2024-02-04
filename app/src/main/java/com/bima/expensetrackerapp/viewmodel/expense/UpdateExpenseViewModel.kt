package com.bima.expensetrackerapp.viewmodel.expense

import androidx.lifecycle.ViewModel
import com.bima.expensetrackerapp.ExpenseTrackerApp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpdateExpenseViewModel @Inject constructor(
    private val context: ExpenseTrackerApp,
): ViewModel() {
}