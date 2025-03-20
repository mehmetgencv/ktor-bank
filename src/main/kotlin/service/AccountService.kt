package com.mehmetgenc.service

import com.mehmetgenc.dto.*
import com.mehmetgenc.exceptions.AccountNotFoundException
import com.mehmetgenc.exceptions.InsufficientFundsException
import com.mehmetgenc.exceptions.InvalidAmountException
import com.mehmetgenc.model.Account
import com.mehmetgenc.model.TransactionType
import com.mehmetgenc.repository.AccountRepository
import com.mehmetgenc.repository.TransactionRepository

class AccountService(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
) {

    suspend fun createAccount(accountDTO: AccountCreateDTO): Int {
        val account = Account(name = accountDTO.name, balance = accountDTO.initialBalance)
        return accountRepository.create(account)
    }

    suspend fun getAccountById(id: Int): Account {
        return getAccountOrThrow(id)
    }

    suspend fun deposit(depositDTO: DepositWithdrawDTO) {
        val account = getAccountOrThrow(depositDTO.id)

        if (depositDTO.amount <= 0) throw InvalidAmountException(depositDTO.amount)

        accountRepository.deposit(account.id!!, depositDTO.amount)
        transactionRepository.recordTransaction(account.id, TransactionType.DEPOSIT, depositDTO.amount)
    }

    suspend fun withdraw(withdrawDTO: DepositWithdrawDTO) {
        val account = getAccountOrThrow(withdrawDTO.id)
        if (withdrawDTO.amount <= 0) throw InvalidAmountException(withdrawDTO.amount)
        if (account.balance < withdrawDTO.amount) throw InsufficientFundsException(account.id!!, account.balance, withdrawDTO.amount)

        accountRepository.withdraw(withdrawDTO.id, withdrawDTO.amount)
        transactionRepository.recordTransaction(withdrawDTO.id, TransactionType.WITHDRAW, withdrawDTO.amount)
    }

    suspend fun transfer(transferDTO: TransferDTO) {
        val fromAccount = getAccountOrThrow(transferDTO.fromId)
        val toAccount = getAccountOrThrow(transferDTO.toId)

        if (transferDTO.amount <= 0) throw InvalidAmountException(transferDTO.amount)
        if (fromAccount.balance < transferDTO.amount) {
            throw InsufficientFundsException(fromAccount.id!!, fromAccount.balance, transferDTO.amount)
        }

        accountRepository.transfer(transferDTO.fromId, transferDTO.toId, transferDTO.amount)

        transactionRepository.recordTransaction(fromAccount.id!!, TransactionType.TRANSFER_OUT, transferDTO.amount)
        transactionRepository.recordTransaction(toAccount.id!!, TransactionType.TRANSFER_IN, transferDTO.amount)
    }


    suspend fun getAllAccounts(): List<Account> {
        return accountRepository.getAllAccounts()
    }

    suspend fun getTransactionHistory(accountId: Int) = transactionRepository.getTransactionsByAccountId(accountId)

    private suspend fun getAccountOrThrow(id: Int): Account {
        return accountRepository.getById(id) ?: throw AccountNotFoundException(id)
    }
}
