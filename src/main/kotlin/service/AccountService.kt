package com.mehmetgenc.service

import com.mehmetgenc.dto.AccountCreateDTO
import com.mehmetgenc.dto.DepositWithdrawDTO
import com.mehmetgenc.dto.TransferDTO
import com.mehmetgenc.model.Account
import com.mehmetgenc.model.TransactionType
import com.mehmetgenc.repository.AccountRepository
import com.mehmetgenc.repository.TransactionRepository

class AccountService(
    private val repository: AccountRepository,
    private val transactionRepository: TransactionRepository
) {

    suspend fun createAccount(accountDTO: AccountCreateDTO): Int {
        return repository.create(accountDTO)
    }

    suspend fun getAccountById(id: Int): Account? {
        return repository.getById(id)
    }

    suspend fun deposit(depositDTO: DepositWithdrawDTO) {
        repository.deposit(depositDTO.id, depositDTO.amount)
        transactionRepository.recordTransaction(depositDTO.id, TransactionType.DEPOSIT, depositDTO.amount)
    }

    suspend fun withdraw(withdrawDTO: DepositWithdrawDTO) {
        repository.withdraw(withdrawDTO.id, withdrawDTO.amount)
        transactionRepository.recordTransaction(withdrawDTO.id, TransactionType.WITHDRAW, withdrawDTO.amount)
    }

    suspend fun transfer(transferDTO: TransferDTO) {
        repository.transfer(transferDTO.fromId, transferDTO.toId, transferDTO.amount)
        transactionRepository.recordTransaction(transferDTO.fromId, TransactionType.TRANSFER_OUT, transferDTO.amount)
        transactionRepository.recordTransaction(transferDTO.toId, TransactionType.TRANSFER_IN, transferDTO.amount)
    }

    suspend fun getAllAccounts(): List<Account> {
        return repository.getAllAccounts()

    }

    suspend fun getTransactionHistory(accountId: Int) = transactionRepository.getTransactionsByAccountId(accountId)
}
