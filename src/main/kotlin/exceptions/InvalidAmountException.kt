package com.mehmetgenc.exceptions

class InvalidAmountException(amount: Double) :
    RuntimeException("Invalid amount: $amount. Amount must be greater than zero.")
