package link.limecode.mywallet.data.repository

import kotlinx.coroutines.flow.Flow
import link.limecode.mywallet.data.local.dao.BalanceDao
import link.limecode.mywallet.data.model.local.balance.Balance
import link.limecode.mywallet.data.model.local.balance.BalanceEntity
import link.limecode.mywallet.domain.repository.BalanceRepository
import link.limecode.mywallet.domain.usecase.balance.GetBalanceUseCase

class BalanceRepositoryRoomImpl(
    private val dao: BalanceDao
): BalanceRepository {

    override fun getBalanceList(): Flow<List<Balance>> {
        return dao.getBalanceList()
    }

    override fun getBalance(param: GetBalanceUseCase.Param): Flow<Balance?> {
        return dao.getBalance(currencyName = param.currencyName)
    }

    override suspend fun saveBalance(balanceEntity: BalanceEntity) {
        dao.insert(data = balanceEntity)
    }

    override suspend fun updateBalance(balanceEntity: BalanceEntity) {
        dao.update(data = balanceEntity)
    }
}

