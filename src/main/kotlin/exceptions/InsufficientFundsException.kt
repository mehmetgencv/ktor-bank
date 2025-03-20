package com.mehmetgenc.exceptions

class InsufficientFundsException(accountId: Int, balance: Double, amount: Double) :
    RuntimeException("Account ID $accountId has insufficient funds: Available $balance, Required $amount")
