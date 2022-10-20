package link.limecode.mywallet.domain.usecase.currency

import kotlinx.coroutines.flow.Flow
import link.limecode.mywallet.data.model.local.currency.Currency
import link.limecode.mywallet.domain.repository.CurrencyRepository

class GetCurrencyListUseCase(
    private val currencyRepository: CurrencyRepository
) {
    data class Param(
        val keyword: String
    )

    fun invoke(param: Param): Flow<List<Currency>> {
        return currencyRepository.getCurrencyList(param = param)
    }
}
