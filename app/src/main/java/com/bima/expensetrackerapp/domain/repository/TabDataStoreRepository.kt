package com.bima.expensetrackerapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface TabDataStoreRepository {
    suspend fun setTabIndex(value:Int)
    suspend fun getTabIndex(key : String): Flow<Int>
}