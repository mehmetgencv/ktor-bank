package com.mehmetgenc

import com.mehmetgenc.config.configureSerialization
import com.mehmetgenc.config.configureDatabase
import com.mehmetgenc.repository.AccountRepository
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

    val accountRepository = AccountRepository()
    val accountService = AccountService(accountRepository)

    accountRoutes(accountService)
}
