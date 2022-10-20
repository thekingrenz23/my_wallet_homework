package link.limecode.mywallet.domain.repository

import kotlinx.coroutines.flow.Flow
import link.limecode.mywallet.data.model.local.balance.Balance
import link.limecode.mywallet.data.model.local.balance.BalanceEntity
import link.limecode.mywallet.domain.usecase.balance.GetBalanceUseCase

interface BalanceRepository {

    fun getBalanceList(): Flow<List<Balance>>

    fun getBalance(param: GetBalanceUseCase.Param): Flow<Balance?>

    suspend fun saveBalance(balanceEntity: BalanceEntity)

    suspend fun updateBalance(balanceEntity: BalanceEntity)
}

