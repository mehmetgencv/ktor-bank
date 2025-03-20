package com.mehmetgenc.repository

import com.mehmetgenc.model.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class AccountRepository {

    suspend fun create(account: Account): Int = dbQuery {
        Accounts.insert {
            it[name] = account.name
            it[balance] = account.balance
        }[Accounts.id]
    }

    suspend fun getById(id: Int): Account? = dbQuery {
        Accounts.selectAll()
            .where { Accounts.id eq id }
            .map {
                Account(
                    id = it[Accounts.id],
                    name = it[Accounts.name],
                    balance = it[Accounts.balance]
                )
            }
            .singleOrNull()
    }

    suspend fun deposit(id: Int, amount: Double) {
        dbQuery {
            Accounts.update({ Accounts.id eq id }) {
                with(SqlExpressionBuilder) {
                    it.update(balance, balance + amount)
                }
            }
        }
    }

    suspend fun withdraw(id: Int, amount: Double) {
        dbQuery {
            Accounts.update({ Accounts.id eq id }) {
                with(SqlExpressionBuilder) {
                    it.update(balance, balance - amount)
                }
            }
        }
    }

    suspend fun transfer(fromId: Int, toId: Int, amount: Double) {
        dbQuery {
            Accounts.update({ Accounts.id eq fromId }) {
                with(SqlExpressionBuilder) {
                    it.update(balance, balance - amount)
                }
            }
            Accounts.update({ Accounts.id eq toId }) {
                with(SqlExpressionBuilder) {
                    it.update(balance, balance + amount)
                }
            }
        }
    }

    suspend fun getAllAccounts(): List<Account> = dbQuery {
        Accounts.selectAll()
            .map {
                Account(
                    id = it[Accounts.id],
                    name = it[Accounts.name],
                    balance = it[Accounts.balance]
                )
            }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
