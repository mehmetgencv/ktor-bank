package com.mehmetgenc.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*

object Transactions : Table() {
    val id = integer("id").autoIncrement()
    val accountId = integer("account_id").references(Accounts.id)
    val type = varchar("type", length = 15) // DEPOSIT, WITHDRAW, TRANSFER
    val amount = double("amount")
    val timestamp = long("timestamp")

    override val primaryKey = PrimaryKey(id)
}

@Serializable
data class Transaction(
    val id: Int? = null,
    val accountId: Int,
    val type: TransactionType,
    val amount: Double,
    val timestamp: Long
)
