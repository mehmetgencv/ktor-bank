package com.mehmetgenc.repository

import com.mehmetgenc.model.*
import com.mehmetgenc.dto.AccountCreateDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class AccountRepository {

    suspend fun create(accountDTO: AccountCreateDTO): Int = dbQuery {
        Accounts.insert {
            it[name] = accountDTO.name
            it[balance] = accountDTO.initialBalance
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

    suspend fun updateBalance(id: Int, newBalance: Double) {
        dbQuery {
            Accounts.update({ Accounts.id eq id }) {
                it[balance] = newBalance
            }
        }
    }

    suspend fun deposit(id: Int, amount: Double) {
        val account = getById(id) ?: throw IllegalArgumentException("Account not found")
        val newBalance = account.balance + amount
        updateBalance(id, newBalance)
    }

    suspend fun withdraw(id: Int, amount: Double) {
        val account = getById(id) ?: throw IllegalArgumentException("Account not found")
        if (account.balance < amount) {
            throw IllegalArgumentException("Insufficient funds")
        }
        val newBalance = account.balance - amount
        updateBalance(id, newBalance)
    }

    suspend fun transfer(fromId: Int, toId: Int, amount: Double) {
        val fromAccount = getById(fromId) ?: throw IllegalArgumentException("Sender account not found")
        val toAccount = getById(toId) ?: throw IllegalArgumentException("Receiver account not found")

        if (fromAccount.balance < amount) {
            throw IllegalArgumentException("Insufficient funds")
        }

        dbQuery {
            Accounts.update({ Accounts.id eq fromId }) {
                it[balance] = fromAccount.balance - amount
            }
            Accounts.update({ Accounts.id eq toId }) {
                it[balance] = toAccount.balance + amount
            }
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    fun getAllAccounts(): List<Account> {
        return runBlocking {
            dbQuery {
                Accounts.selectAll()
                    .map {
                        Account(
                            id = it[Accounts.id],
                            name = it[Accounts.name],
                            balance = it[Accounts.balance]
                        )
                    }
            }
        }

    }
}
