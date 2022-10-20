package link.limecode.mywallet.domain.usecase.balance

import kotlinx.coroutines.flow.Flow
import link.limecode.mywallet.data.model.local.balance.Balance
import link.limecode.mywallet.domain.repository.BalanceRepository

class GetBalanceListUseCase(
    private val balanceRepository: BalanceRepository
) {

    fun invoke(): Flow<List<Balance>> {
        return balanceRepository.getBalanceList()
    }
}

