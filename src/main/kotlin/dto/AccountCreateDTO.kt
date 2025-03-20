package com.mehmetgenc.dto

import kotlinx.serialization.Serializable

@Serializable
data class AccountCreateDTO(
    val name: String,
    val initialBalance: Double
)
