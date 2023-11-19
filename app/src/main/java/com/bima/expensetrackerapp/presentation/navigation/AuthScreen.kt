package com.bima.expensetrackerapp.presentation.navigation


sealed class AuthScreen(val route:String,val name:String ,) {
    object Title: AuthScreen("title_screen","Title")
    object Login: AuthScreen("login_screen","Login")
}
