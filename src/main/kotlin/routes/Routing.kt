package com.mehmetgenc.routes

import com.mehmetgenc.dto.AccountCreateDTO
import com.mehmetgenc.dto.DepositWithdrawDTO
import com.mehmetgenc.dto.TransferDTO
import com.mehmetgenc.service.AccountService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.accountRoutes(accountService: AccountService) {
    routing {
        post("/accounts") {
            val accountDTO = call.receive<AccountCreateDTO>()
            val id = accountService.createAccount(accountDTO)
            call.respond(HttpStatusCode.Created, id)
        }

        get("/accounts/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val account = id?.let { accountService.getAccountById(it) }
            if (account != null) {
                call.respond(HttpStatusCode.OK, account)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        get("/accounts") {
            val account = accountService.getAllAccounts()
            if (account != null) {
                call.respond(HttpStatusCode.OK, account)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        put("/accounts/deposit") {
            val depositDTO = call.receive<DepositWithdrawDTO>()
            accountService.deposit(depositDTO)
            call.respond(HttpStatusCode.OK, "Deposit successful")
        }

        put("/accounts/withdraw") {
            val withdrawDTO = call.receive<DepositWithdrawDTO>()
            try {
                accountService.withdraw(withdrawDTO)
                call.respond(HttpStatusCode.OK, "Withdrawal successful")
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, e.message ?: "Error")
            }
        }

        post("/accounts/transfer") {
            val transferDTO = call.receive<TransferDTO>()
            try {
                accountService.transfer(transferDTO)
                call.respond(HttpStatusCode.OK, "Transfer successful")
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, e.message ?: "Error")
            }
        }
    }
}
