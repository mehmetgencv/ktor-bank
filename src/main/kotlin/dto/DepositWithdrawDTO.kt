package com.mehmetgenc.dto

import kotlinx.serialization.Serializable

@Serializable
data class DepositWithdrawDTO(
    val id: Int,
    val amount: Double
)
