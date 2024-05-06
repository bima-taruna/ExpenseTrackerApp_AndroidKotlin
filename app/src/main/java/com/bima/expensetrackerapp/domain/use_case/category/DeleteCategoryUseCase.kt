package com.bima.expensetrackerapp.domain.use_case.category

import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend fun execute(id:String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        val result = withContext(Dispatchers.IO) {
            repository.deleteCategory(id)
        }
        emit(Resource.Success(result))
    }.catch { e ->
        when (e) {
            is IOException -> emit(Resource.Error("Couldn't reach the server. Please check your internet connection."))
            else -> emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
        }
    }
}