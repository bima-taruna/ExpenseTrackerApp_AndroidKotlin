package com.bima.expensetrackerapp.di

import com.bima.expensetrackerapp.data.repositoryImpl.BalanceRepositoryImpl
import com.bima.expensetrackerapp.domain.repository.BalanceRepository
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
}