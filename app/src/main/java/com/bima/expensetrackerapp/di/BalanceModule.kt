package com.bima.expensetrackerapp.di

import com.bima.expensetrackerapp.data.repositoryImpl.BalanceRepositoryImpl
import com.bima.expensetrackerapp.domain.repository.BalanceRepository
import com.bima.expensetrackerapp.domain.repository.UserProfileRepository
import com.bima.expensetrackerapp.domain.use_case.balance.BalanceUseCases
import com.bima.expensetrackerapp.domain.use_case.balance.GetBalanceUseCase
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
object BalanceModule {
    @Provides
    @Singleton
    fun provideBalanceRepository(postgrest: Postgrest): BalanceRepository {
        return BalanceRepositoryImpl(postgrest)
    }

    @Singleton
    @Provides
    fun provideBalanceUseCases(repository: BalanceRepository): BalanceUseCases{
        return BalanceUseCases(
            getBalance = GetBalanceUseCase(repository)
        )
    }
}