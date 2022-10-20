package link.limecode.mywallet.domain.usecase.currency

import link.limecode.mywallet.data.model.local.currency.CurrencyEntity
import link.limecode.mywallet.domain.repository.CurrencyRepository

class AddCurrencyUseCase(
    private val currencyRepository: CurrencyRepository
) {

    suspend fun invoke(currency: CurrencyEntity) {
        currencyRepository.insertCurrency(currency = currency)
    }
}

