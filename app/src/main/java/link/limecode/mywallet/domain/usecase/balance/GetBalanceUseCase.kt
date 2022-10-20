package link.limecode.mywallet.domain.usecase.balance

import kotlinx.coroutines.flow.Flow
import link.limecode.mywallet.data.model.local.balance.Balance
import link.limecode.mywallet.domain.repository.BalanceRepository

class GetBalanceUseCase(
    private val balanceRepository: BalanceRepository
) {
    data class Param(
        val currencyName: String
    )

    fun invoke(param: Param): Flow<Balance?> {
        return balanceRepository.getBalance(param = param)
    }
}

