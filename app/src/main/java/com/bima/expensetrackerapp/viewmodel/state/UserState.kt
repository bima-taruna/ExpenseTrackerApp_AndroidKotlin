package com.bima.expensetrackerapp.viewmodel.state

import com.bima.expensetrackerapp.domain.model.UserProfile

data class UserState(
    val isLoading:Boolean = false,
    val user: UserProfile? = null,
    val error:String = ""
)