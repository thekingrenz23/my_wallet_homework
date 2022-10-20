package link.limecode.mywallet.domain.repository

import link.limecode.mywallet.app.util.Result
import link.limecode.mywallet.data.model.remote.currencyexchangerateapi.CurrencyExchangeRate

interface CurrencyExchangeRateRepository {

    suspend fun getExchangeRate(): Result<CurrencyExchangeRate>
}

