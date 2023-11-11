package com.bima.expensetrackerapp.domain.use_case.tab_mode

import com.bima.expensetrackerapp.domain.repository.TabDataStoreRepository
import javax.inject.Inject

class SetTabIndex @Inject constructor(
    private val repository: TabDataStoreRepository
) {
    suspend operator fun invoke(value:Int) {
        return repository.setTabIndex(value)
    }
}