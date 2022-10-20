package link.limecode.mywallet.domain.repository

import kotlinx.coroutines.flow.Flow
import link.limecode.mywallet.data.model.local.currency.Currency
import link.limecode.mywallet.data.model.local.currency.CurrencyEntity
import link.limecode.mywallet.domain.usecase.currency.GetCurrencyListUseCase
import link.limecode.mywallet.domain.usecase.currency.GetCurrencyUseCase

interface CurrencyRepository {

    fun getCurrencyList(param: GetCurrencyListUseCase.Param): Flow<List<Currency>>

    fun getCurrency(param: GetCurrencyUseCase.Param): Flow<Currency?>

    suspend fun insertCurrency(currency: CurrencyEntity)

    suspend fun insertCurrencies(currencyList: List<CurrencyEntity>)
}
