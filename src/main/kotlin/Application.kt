package com.mehmetgenc

import com.mehmetgenc.config.configureSerialization
import com.mehmetgenc.config.configureDatabase
import com.mehmetgenc.config.configureExceptionHandling
import com.mehmetgenc.repository.AccountRepository
import com.mehmetgenc.repository.TransactionRepository
import com.mehmetgenc.routes.accountRoutes
import com.mehmetgenc.service.AccountService
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureDatabase()
    configureExceptionHandling()

    val accountRepository = AccountRepository()
    val transactionRepository = TransactionRepository()
    val accountService = AccountService(accountRepository, transactionRepository)

    accountRoutes(accountService)
}
