package com.mehmetgenc.service

import com.mehmetgenc.dto.AccountCreateDTO
import com.mehmetgenc.dto.DepositWithdrawDTO
import com.mehmetgenc.dto.TransferDTO
import com.mehmetgenc.model.Account
import com.mehmetgenc.repository.AccountRepository

class AccountService(private val repository: AccountRepository) {

    suspend fun createAccount(accountDTO: AccountCreateDTO): Int {
        return repository.create(accountDTO)
    }

    suspend fun getAccountById(id: Int): Account? {
        return repository.getById(id)
    }

    suspend fun deposit(depositDTO: DepositWithdrawDTO) {
        repository.deposit(depositDTO.id, depositDTO.amount)
    }

    suspend fun withdraw(withdrawDTO: DepositWithdrawDTO) {
        repository.withdraw(withdrawDTO.id, withdrawDTO.amount)
    }

    suspend fun transfer(transferDTO: TransferDTO) {
        repository.transfer(transferDTO.fromId, transferDTO.toId, transferDTO.amount)
    }

    fun getAllAccounts(): List<Account> {
        return repository.getAllAccounts()

    }
}
