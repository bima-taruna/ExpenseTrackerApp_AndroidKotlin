package com.bima.expensetrackerapp.data.repositoryImpl

import com.bima.expensetrackerapp.data.remote.TransactionDto
import com.bima.expensetrackerapp.domain.model.Expense
import com.bima.expensetrackerapp.domain.repository.TransactionRepository
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : TransactionRepository {
    override suspend fun getTransactions(type:String): List<TransactionDto> {
        return postgrest[type].select(Columns.ALL).decodeList()
    }

    override suspend fun createTransaction(transaction: Expense, type: String): Boolean {
        return try {
            val transactionDto = TransactionDto(
                name = transaction.name,
                description = transaction.description,
                amount = transaction.amount,
                date = transaction.date,
                categoryId = transaction.categoryId,
            )
            postgrest[type].insert(transactionDto)
            true
        } catch (e:java.lang.Exception) {
            throw e
        }

    }

    override suspend fun deleteTransaction(id: String, type: String): Boolean {
        return try {
            postgrest.from(type).delete {
                eq("id", id)
            }
            true
        } catch (e:java.lang.Exception) {
            throw e
        }
    }
}