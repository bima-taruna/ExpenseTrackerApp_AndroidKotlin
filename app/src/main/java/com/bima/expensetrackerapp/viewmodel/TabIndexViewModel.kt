package com.bima.expensetrackerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bima.expensetrackerapp.domain.use_case.tab_mode.GetTabIndex
import com.bima.expensetrackerapp.domain.use_case.tab_mode.SetTabIndex
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TabIndexViewModel @Inject constructor(
    private val getTabIndex: GetTabIndex,
    private val setTabIndex: SetTabIndex
) :ViewModel() {
    private val _tabIndex = MutableStateFlow<Int?>(0)
    val tabIndex = _tabIndex.asStateFlow()

    init {
        saveTabIndex(0)
        getTabIndex()
    }

    fun saveTabIndex(value:Int) {
        viewModelScope.launch {
            setTabIndex.invoke(value)
        }
    }

     private fun getTabIndex() {
        viewModelScope.launch {
            getTabIndex("tabIndex").collect {
                _tabIndex.value = it
            }
        }
    }
}