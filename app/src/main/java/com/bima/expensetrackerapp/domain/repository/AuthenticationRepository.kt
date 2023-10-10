package com.bima.expensetrackerapp.domain.repository

import com.bima.expensetrackerapp.common.Resource
import io.github.jan.supabase.gotrue.user.UserSession
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    fun signIn(email:String, password:String) : Flow<Resource<Boolean>>
    suspend fun signUp(email:String, password:String) : Boolean
    suspend fun getSession(): UserSession?
    suspend fun signOut()
}

