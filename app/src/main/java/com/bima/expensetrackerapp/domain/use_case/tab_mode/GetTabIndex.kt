package com.bima.expensetrackerapp.domain.use_case.tab_mode

import com.bima.expensetrackerapp.domain.repository.TabDataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTabIndex @Inject constructor(
    private val repository: TabDataStoreRepository
) {
    suspend operator fun invoke(key:String) : Flow<Int> {
        return repository.getTabIndex(key)
    }
}