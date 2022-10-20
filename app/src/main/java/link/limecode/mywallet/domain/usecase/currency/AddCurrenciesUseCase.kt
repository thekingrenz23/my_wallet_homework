package link.limecode.mywallet.domain.usecase.currency

import link.limecode.mywallet.data.model.local.currency.CurrencyEntity
import link.limecode.mywallet.domain.repository.CurrencyRepository

class AddCurrenciesUseCase(
    private val currencyRepository: CurrencyRepository
) {

    suspend operator fun invoke(currencyList: List<CurrencyEntity>) {
        currencyRepository.insertCurrencies(currencyList = currencyList)
    }
}
