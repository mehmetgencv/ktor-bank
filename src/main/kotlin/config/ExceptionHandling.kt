package com.mehmetgenc.config

import com.mehmetgenc.exceptions.AccountNotFoundException
import com.mehmetgenc.exceptions.InsufficientFundsException
import com.mehmetgenc.exceptions.InvalidAmountException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureExceptionHandling() {
    install(StatusPages) {
        exception<AccountNotFoundException> { call, cause ->
            call.respond(HttpStatusCode.NotFound, mapOf("error" to cause.message))
        }
        exception<InsufficientFundsException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to cause.message))
        }
        exception<InvalidAmountException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to cause.message)) // ✅ Yeni exception eklendi
        }
        exception<IllegalArgumentException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to cause.localizedMessage))
        }
        exception<ArithmeticException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Mathematical error: ${cause.localizedMessage}"))
        }
        exception<Throwable> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "An unexpected error occurred"))
        }
    }
}
