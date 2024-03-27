package com.bima.expensetrackerapp.viewmodel.state.category

data class EventCategoryState(
    val isLoading:Boolean = false,
    val category: Boolean = false,
    val error:String = ""
)
