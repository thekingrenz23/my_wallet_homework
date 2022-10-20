package link.limecode.mywallet.data.repository

import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import link.limecode.mywallet.app.exception.NoInternetConnection
import link.limecode.mywallet.app.exception.SomethingWentWrong
import link.limecode.mywallet.app.util.Result
import link.limecode.mywallet.data.model.remote.currencyexchangerateapi.CurrencyExchangeRate
import link.limecode.mywallet.data.remote.service.CurrencyExchangeRateService
import link.limecode.mywallet.domain.repository.CurrencyExchangeRateRepository
import retrofit2.HttpException

class CurrencyExchangeRateApiImpl(
    private val currencyExchangeRateService: CurrencyExchangeRateService
) : CurrencyExchangeRateRepository {

    override suspend fun getExchangeRate(): Result<CurrencyExchangeRate> {
        return withContext(Dispatchers.IO) {
            val response = try {
                currencyExchangeRateService.currencyExchangeRateApi.getExchangeRate()
            } catch (e: IOException) {
                return@withContext Result.Error(NoInternetConnection(message = e.message))
            } catch (e: HttpException) {
                return@withContext Result.Error(SomethingWentWrong(message = e.message()))
            }

            if (response.isSuccessful && response.body() != null) {
                return@withContext Result.Success(response.body()!!)
            } else {
                return@withContext Result.Error(SomethingWentWrong())
            }

        }
    }
}

