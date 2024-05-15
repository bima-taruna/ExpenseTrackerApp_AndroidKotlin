package com.bima.expensetrackerapp.viewmodel.category

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bima.expensetrackerapp.ExpenseTrackerApp
import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.common.ValidationEvent
import com.bima.expensetrackerapp.common.form_event.CategoryFormEvent
import com.bima.expensetrackerapp.domain.use_case.category.UpdateCategoryUseCase
import com.bima.expensetrackerapp.domain.use_case.form_validation.ValidateName
import com.bima.expensetrackerapp.viewmodel.state.category.EventCategoryState
import com.bima.expensetrackerapp.viewmodel.state.form.CategoryFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UpdateCategoryViewModel @Inject constructor(
    private val context:ExpenseTrackerApp,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val validateName: ValidateName
): ViewModel() {
    private val _updateCategoryState = MutableStateFlow(EventCategoryState())
    val updateCategoryState = _updateCategoryState.asStateFlow()

    private val _updateCategoryFormState = MutableStateFlow(CategoryFormState())
    val updateCategoryFormState = _updateCategoryFormState.asStateFlow()

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: CategoryFormEvent) {
        when(event) {
            is CategoryFormEvent.NameChanged -> {
                _updateCategoryFormState.update {
                    it.copy(name = event.name)
                }
            }
            is CategoryFormEvent.Submit -> {
                submitData()
            }
        }
    }

    fun updateCategory(id:String, name:String) {
        viewModelScope.launch {
            updateCategoryUseCase.execute(id,name).onEach { result ->
                when(result) {
                    is Resource.Success -> {
                        _updateCategoryState.update {
                            it.copy(isLoading = false, category = result.data ?: false)
                        }
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Error -> {
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                        _updateCategoryState.update {
                            it.copy(isLoading = false, error = result.message.toString())
                        }
                    }
                    is Resource.Loading -> {
                        _updateCategoryState.update {
                            it.copy(isLoading = true)
                        }
                    }
                }
            }.collect()
        }
    }

    private fun submitData() {
        val nameResult = validateName.execute(_updateCategoryFormState.value.name)
        if (!nameResult.successful) {
            _updateCategoryFormState.update {
                it.copy(nameError = nameResult.errorMessage)
            }
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }
}