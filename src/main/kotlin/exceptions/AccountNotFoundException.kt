package com.mehmetgenc.exceptions

class AccountNotFoundException(accountId: Int) : RuntimeException("Account with ID $accountId not found")
