package com.bima.expensetrackerapp.data.repositoryImpl

import com.bima.expensetrackerapp.domain.repository.AuthenticationRepository
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserSession
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val goTrue: GoTrue
) : AuthenticationRepository {
    override suspend fun signIn(email: String, password: String): Boolean {
       return try {
            goTrue.loginWith(Email) {
                this.email = email
                this.password = password
            }
            true
        } catch (e: Exception) {
           false
        }
    }

    override suspend fun signUp(email: String, password: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getSession(): UserSession? {
        return goTrue.currentSessionOrNull()
    }

    override suspend fun signOut() {
        goTrue.logout()
    }
}