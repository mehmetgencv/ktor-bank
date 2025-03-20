package com.mehmetgenc.config

import com.mehmetgenc.model.Accounts
import com.mehmetgenc.model.Transactions
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabase() {
    val database = Database.connect(
        url = "jdbc:h2:file:./data/bankdb;DB_CLOSE_DELAY=-1;AUTO_SERVER=TRUE",
        driver = "org.h2.Driver",
        user = "root",
        password = ""
    )
    transaction {
        SchemaUtils.create(Accounts, Transactions)

    }

}
