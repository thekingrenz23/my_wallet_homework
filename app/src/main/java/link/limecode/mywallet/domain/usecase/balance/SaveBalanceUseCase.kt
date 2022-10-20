package link.limecode.mywallet.domain.usecase.balance

import link.limecode.mywallet.data.model.local.balance.BalanceEntity
import link.limecode.mywallet.domain.repository.BalanceRepository

class SaveBalanceUseCase(
    private val balanceRepository: BalanceRepository
) {

    suspend operator fun invoke(balanceEntity: BalanceEntity) {
        balanceRepository.saveBalance(balanceEntity = balanceEntity)
    }
}
