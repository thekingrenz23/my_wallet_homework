package link.limecode.mywallet.domain.usecase.currencyexchangerate

import kotlin.reflect.full.memberProperties
import link.limecode.mywallet.app.util.Result
import link.limecode.mywallet.data.model.remote.currencyexchangerateapi.Currency
import link.limecode.mywallet.data.model.remote.currencyexchangerateapi.CurrencyExChangeRateList
import link.limecode.mywallet.domain.repository.CurrencyExchangeRateRepository

class GetCurrencyExchangeRateUseCase(
    private val currencyExchangeRateRepository: CurrencyExchangeRateRepository
) {

    suspend fun invoke(): Result<CurrencyExChangeRateList> {
        when (val result = currencyExchangeRateRepository.getExchangeRate()) {
            is Result.Success -> {
                val currencyExchangeRate = result.data
                val currencyList = mutableListOf<Currency>()
                currencyExchangeRate.rates::class.memberProperties.forEach {
                    currencyList.add(
                        Currency(
                            currencyName = it.name,
                            rate = it.getter.call(currencyExchangeRate.rates) as Double?
                        )
                    )
                }
                return Result.Success(
                    CurrencyExChangeRateList(
                        base = currencyExchangeRate.base,
                        currencyList = currencyList
                    )
                )
            }
            is Result.Error -> {
                return Result.Error(result.exception)
            }
        }
    }
}

