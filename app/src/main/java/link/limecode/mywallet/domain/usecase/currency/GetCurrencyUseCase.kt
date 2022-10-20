package link.limecode.mywallet.domain.usecase.currency

import kotlinx.coroutines.flow.Flow
import link.limecode.mywallet.data.model.local.currency.Currency
import link.limecode.mywallet.domain.repository.CurrencyRepository

class GetCurrencyUseCase(
    private val currencyRepository: CurrencyRepository
) {

    data class Param(
        val currencyName: String
    )

    operator fun invoke(param: Param): Flow<Currency?> {
        return currencyRepository.getCurrency(param = param)
    }
}
