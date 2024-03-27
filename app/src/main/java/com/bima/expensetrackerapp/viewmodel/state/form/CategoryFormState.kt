package com.bima.expensetrackerapp.viewmodel.state.form

import com.bima.expensetrackerapp.common.UiText

data class CategoryFormState(
    val name:String = "",
    val nameError:UiText? = null
)
