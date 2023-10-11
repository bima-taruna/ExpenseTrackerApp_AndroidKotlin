package com.bima.expensetrackerapp.domain.repository

import io.github.jan.supabase.gotrue.user.UserSession

interface AuthenticationRepository {
    suspend fun signIn(email:String, password:String) : Boolean
    suspend fun signUp(email:String, password:String) : Boolean
    suspend fun getSession(): UserSession?
    suspend fun signOut()
}

