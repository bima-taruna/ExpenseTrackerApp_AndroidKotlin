package com.bima.expensetrackerapp.di

import android.content.Context
import com.bima.expensetrackerapp.data.repositoryImpl.TabDataStoreRepositoryImpl
import com.bima.expensetrackerapp.domain.repository.TabDataStoreRepository
import com.bima.expensetrackerapp.domain.use_case.tab_mode.GetTabIndex
import com.bima.expensetrackerapp.domain.use_case.tab_mode.SetTabIndex
import com.bima.expensetrackerapp.domain.use_case.tab_mode.TabIndexUsesCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {

    @Singleton
    @Provides
    fun provideTabDataStoreRepository(
        @ApplicationContext app: Context
    ): TabDataStoreRepository {
        return TabDataStoreRepositoryImpl(app)
    }

    @Provides
    @Singleton
    fun provideUiDataStore(repository: TabDataStoreRepository): TabIndexUsesCases {
        return TabIndexUsesCases(
            getTabIndex = GetTabIndex(repository),
            setTabIndex = SetTabIndex(repository)
        )
    }
}