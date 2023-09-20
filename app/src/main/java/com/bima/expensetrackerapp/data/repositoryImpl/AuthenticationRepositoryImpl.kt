package com.bima.expensetrackerapp.data.repositoryImpl

import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.domain.AuthenticationRepository
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val goTrue: GoTrue
) : AuthenticationRepository {
    override fun signIn(email: String, password: String): Flow<Resource<Boolean>> = flow {
       try {
            emit(Resource.Loading())
            goTrue.loginWith(Email) {
                this.email = email
                this.password = password
            }
            emit(Resource.Success(true))
        } catch (e: Exception) {
           emit(Resource.Error(e.localizedMessage ?: "error occured"))
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