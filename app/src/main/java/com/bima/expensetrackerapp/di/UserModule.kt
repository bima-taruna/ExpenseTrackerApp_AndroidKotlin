package com.bima.expensetrackerapp.di

import com.bima.expensetrackerapp.data.repositoryImpl.UserProfileRepositoryImpl
import com.bima.expensetrackerapp.domain.repository.AuthenticationRepository
import com.bima.expensetrackerapp.domain.repository.UserProfileRepository
import com.bima.expensetrackerapp.domain.use_case.auth.AuthUseCases
import com.bima.expensetrackerapp.domain.use_case.auth.GetSessionUseCase
import com.bima.expensetrackerapp.domain.use_case.auth.IsUserLogInUseCase
import com.bima.expensetrackerapp.domain.use_case.auth.SignInUseCase
import com.bima.expensetrackerapp.domain.use_case.auth.SignOutUseCase
import com.bima.expensetrackerapp.domain.use_case.user.GetProfileUseCase
import com.bima.expensetrackerapp.domain.use_case.user.UserUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UserModule {
    @Singleton
    @Provides
    fun provideAuthUseCases(repository: AuthenticationRepository): AuthUseCases {
        return AuthUseCases(
            signIn = SignInUseCase(repository),
            getSession = GetSessionUseCase(repository),
            signOut = SignOutUseCase(repository),
            isUserLogIn = IsUserLogInUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideUserProfileRepository(postgrest: Postgrest): UserProfileRepository {
        return UserProfileRepositoryImpl(postgrest)
    }

    @Singleton
    @Provides
    fun provideUserUseCases(repository: UserProfileRepository): UserUseCases {
        return UserUseCases(
            getProfile = GetProfileUseCase(repository)
        )
    }
}