package link.limecode.mywallet.data.remote.service

import link.limecode.mywallet.data.remote.api.CurrencyExchangeRateApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CurrencyExchangeRateService(private val apiUrl: String) {

    val currencyExchangeRateApi: CurrencyExchangeRateApi by lazy {
        Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyExchangeRateApi::class.java)
    }
}
