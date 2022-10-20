package link.limecode.mywallet.data.remote.api

import link.limecode.mywallet.data.model.remote.currencyexchangerateapi.CurrencyExchangeRate
import retrofit2.Response
import retrofit2.http.GET

interface CurrencyExchangeRateApi {

    @GET("currency-exchange-rates")
    suspend fun getExchangeRate(): Response<CurrencyExchangeRate>
}

