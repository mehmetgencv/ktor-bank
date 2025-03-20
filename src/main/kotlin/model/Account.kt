package com.mehmetgenc.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*

object Accounts : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", length = 50)
    val balance = double("balance")

    override val primaryKey = PrimaryKey(id)
}

@Serializable
data class Account(
    val id: Int? = null,
    val name: String,
    val balance: Double
)