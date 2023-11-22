package com.bima.expensetrackerapp.domain.use_case.category

import com.bima.expensetrackerapp.common.Resource
import com.bima.expensetrackerapp.data.remote.CategoryDto
import com.bima.expensetrackerapp.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class GetExpenseCategoryByIdUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend fun execute(id:String):Flow<Resource<CategoryDto>> = flow {
        try {
            emit(Resource.Loading())
            val result = withContext(Dispatchers.IO) {
                categoryRepository.getExpenseCategoryById(id)
            }
            emit(Resource.Success(result))
        } catch (e:Exception) {
            emit(Resource.Error(e.localizedMessage ?: "error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("couldn't reach server, please check your internet connection" ))
        }
    }
}