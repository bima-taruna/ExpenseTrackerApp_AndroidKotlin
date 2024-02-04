package com.bima.expensetrackerapp.data.repositoryImpl

import com.bima.expensetrackerapp.data.remote.TransactionDto
import com.bima.expensetrackerapp.domain.model.Transaction
import com.bima.expensetrackerapp.domain.repository.TransactionRepository
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest,
) : TransactionRepository {
    override suspend fun getTransactions(type: String): List<TransactionDto> {
        return postgrest[type].select(Columns.ALL).decodeList()
    }

    override suspend fun getTransactionById(type: String, id: String): TransactionDto {
        return postgrest[type].select(Columns.ALL) {
            eq("id", id)
        }.decodeSingle()
    }

    override suspend fun createTransaction(transaction: Transaction, type: String): Boolean {
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
        } catch (e: java.lang.Exception) {
            throw e
        }

    }

    override suspend fun deleteTransaction(id: String, type: String): Boolean {
        return try {
            postgrest.from(type).delete {
                eq("id", id)
            }
            true
        } catch (e: java.lang.Exception) {
            throw e
        }
    }

    override suspend fun updateTransaction(
        id: String,
        data:Transaction,
        type: String,
    ): Boolean {
        return try {
            postgrest.from(type).update({
                set("name", data.name)
                set("description", data.description)
                set("categoryId", data.categoryId)
                set("amount", data.amount)
                set("date", data.date)
            }) {
                eq("id", id)
            }
            true
        } catch (e: java.lang.Exception) {
            throw e
        }
    }


}