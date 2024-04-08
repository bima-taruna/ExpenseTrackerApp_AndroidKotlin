package com.bima.expensetrackerapp.common.form_event

sealed class CategoryFormEvent {
    data class NameChanged(val name:String) : CategoryFormEvent()
    data object Submit:CategoryFormEvent()
}