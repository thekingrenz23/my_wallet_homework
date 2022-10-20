package link.limecode.mywallet.domain.usecase.balance

import link.limecode.mywallet.data.model.local.balance.BalanceEntity
import link.limecode.mywallet.domain.repository.BalanceRepository

class UpdateBalanceUseCase(
    private val balanceRepository: BalanceRepository
) {

    suspend fun invoke(balanceEntity: BalanceEntity) {
        balanceRepository.updateBalance(balanceEntity = balanceEntity)
    }
}
