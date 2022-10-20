package link.limecode.mywallet.data.repository

import kotlinx.coroutines.flow.Flow
import link.limecode.mywallet.data.local.dao.CurrencyDao
import link.limecode.mywallet.data.model.local.currency.Currency
import link.limecode.mywallet.data.model.local.currency.CurrencyEntity
import link.limecode.mywallet.domain.repository.CurrencyRepository
import link.limecode.mywallet.domain.usecase.currency.GetCurrencyListUseCase
import link.limecode.mywallet.domain.usecase.currency.GetCurrencyUseCase

class CurrencyRepositoryRoomImpl(
    private val dao: CurrencyDao
) : CurrencyRepository {

    override fun getCurrencyList(param: GetCurrencyListUseCase.Param): Flow<List<Currency>> {
        return dao.getCurrencyList(keyword = param.keyword)
    }

    override suspend fun insertCurrency(currency: CurrencyEntity) {
        dao.insert(data = currency)
    }

    override suspend fun insertCurrencies(currencyList: List<CurrencyEntity>) {
        dao.insert(data = currencyList)
    }

    override fun getCurrency(param: GetCurrencyUseCase.Param): Flow<Currency?> {
       return dao.getCurrency(currencyName = param.currencyName)
    }
}

