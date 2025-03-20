package com.mehmetgenc.repository

import com.mehmetgenc.model.*
import com.mehmetgenc.model.Transaction
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class TransactionRepository {

    suspend fun recordTransaction(accountId: Int, type: TransactionType, amount: Double) {
        dbQuery {
            Transactions.insert {
                it[Transactions.accountId] = accountId
                it[Transactions.type] = type.name
                it[Transactions.amount] = amount
                it[Transactions.timestamp] = System.currentTimeMillis()
            }
        }
    }

    suspend fun getTransactionsByAccountId(accountId: Int): List<Transaction> = dbQuery {
        Transactions.selectAll()
            .where { Transactions.accountId eq accountId }
            .map {
                Transaction(
                    id = it[Transactions.id],
                    accountId = it[Transactions.accountId],
                    type = TransactionType.valueOf(it[Transactions.type]),
                    amount = it[Transactions.amount],
                    timestamp = it[Transactions.timestamp]
                )
            }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
