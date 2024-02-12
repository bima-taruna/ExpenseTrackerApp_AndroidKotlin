package com.bima.expensetrackerapp.data.repositoryImpl

import android.content.Context
import com.bima.expensetrackerapp.common.SharedPreferencesHelper
import com.bima.expensetrackerapp.domain.repository.AuthenticationRepository
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserSession
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val goTrue: GoTrue
) : AuthenticationRepository {
    override suspend fun signIn(context: Context,email: String, password: String): Boolean {
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

    override fun saveToken(context: Context) {
        val accessToken = goTrue.currentAccessTokenOrNull()
        val sharedPref = SharedPreferencesHelper(context)
        sharedPref.saveStringData("accessToken", accessToken)
    }

    override fun getToken(context: Context): String? {
        val sharedPref = SharedPreferencesHelper(context)
        return sharedPref.getStringData("accessToken")
    }

    override suspend fun isUserLogin(context: Context): Boolean {
        return try {
            val token = getToken(context)
            if (token.isNullOrEmpty()) {
                false
            } else {
                goTrue.retrieveUser(token)
                goTrue.refreshCurrentSession()
                saveToken(context)
                true
            }
        } catch (e: Exception) {
            false
        }
    }
}