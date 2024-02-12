package com.bima.expensetrackerapp.domain.repository

import android.content.Context
import io.github.jan.supabase.gotrue.user.UserSession

interface AuthenticationRepository {
    suspend fun signIn(context: Context,email:String, password:String) : Boolean
    suspend fun signUp(email:String, password:String) : Boolean
    suspend fun getSession(): UserSession?
    suspend fun signOut()

    fun saveToken(context:Context)
    fun getToken(context: Context): String?
    suspend fun isUserLogin(context: Context):Boolean
}

