package com.mehmetgenc.dto

import kotlinx.serialization.Serializable

@Serializable
data class TransferDTO(
    val fromId: Int,
    val toId: Int,
    val amount: Double
)
