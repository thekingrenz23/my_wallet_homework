package link.limecode.mywallet.presentation.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import link.limecode.mywallet.BuildConfig
import link.limecode.mywallet.data.model.local.balance.BalanceEntity
import link.limecode.mywallet.data.model.local.currency.CurrencyEntity
import link.limecode.mywallet.data.model.local.preference.PreferenceEntity
import link.limecode.mywallet.domain.usecase.balance.SaveBalanceUseCase
import link.limecode.mywallet.domain.usecase.currency.AddCurrenciesUseCase
import link.limecode.mywallet.domain.usecase.currency.GetCurrencyListUseCase
import link.limecode.mywallet.domain.usecase.currency.GetCurrencyUseCase
import link.limecode.mywallet.domain.usecase.preference.AddFreeConversionUseCase
import link.limecode.mywallet.domain.usecase.supportedcurrency.GetSupportedCurrencyListUseCase

@Suppress("LongParameterList")
class MyWalletAppViewModel(
    val closeSplashScreen: () -> Unit,
    val addCurrenciesUseCase: AddCurrenciesUseCase,
    val getCurrencyListUseCase: GetCurrencyListUseCase,
    val getSupportedCurrencyListUseCase: GetSupportedCurrencyListUseCase,
    val getCurrencyUseCase: GetCurrencyUseCase,
    val saveBalanceUseCase: SaveBalanceUseCase,
    val addFreeConversionUseCase: AddFreeConversionUseCase
) : ViewModel() {

    init {
        initApp()
    }

    private fun initApp() {
        viewModelScope.launch {
            val currencyList =
                getCurrencyListUseCase.invoke(param = GetCurrencyListUseCase.Param(keyword = ""))
                    .first()

            if (currencyList.isEmpty()) {
                val supportedCurrencyList = mapSupportedCurrency()
                addCurrenciesUseCase(supportedCurrencyList)
                val defaultCurrency = getCurrencyUseCase(
                    GetCurrencyUseCase.Param(currencyName = BuildConfig.INITIAL_BALANCE_CURRENCY)
                ).first()

                if (defaultCurrency != null) {
                    saveBalanceUseCase(
                        BalanceEntity(
                            id = null,
                            defaultCurrency.id,
                            BuildConfig.INITIAL_BALANCE.toDoubleOrNull() ?: 0.0
                        )
                    )
                    addFreeConversionUseCase(
                        PreferenceEntity(
                            id = null,
                            freeConversion = BuildConfig.INITIAL_FREE_CONVERSION_COUNT.toIntOrNull()
                                ?: 0
                        )
                    )
                }
            }

            closeSplashScreen()
        }
    }

    private suspend fun mapSupportedCurrency(): List<CurrencyEntity> {
        val mapJob = viewModelScope.async(Dispatchers.IO) {
            val currencies = mutableListOf<CurrencyEntity>()
            val listOfSupportedCurrency = getSupportedCurrencyListUseCase.invoke()

            for (item in listOfSupportedCurrency) {
                currencies.add(
                    CurrencyEntity(
                        id = null,
                        currencyName = item
                    )
                )
            }

            return@async currencies.toList()
        }
        return mapJob.await()
    }

    companion object {
        @Suppress("LongParameterList")
        fun provideFactory(
            closeSplashScreen: () -> Unit,
            addCurrenciesUseCase: AddCurrenciesUseCase,
            getCurrencyListUseCase: GetCurrencyListUseCase,
            getSupportedCurrencyListUseCase: GetSupportedCurrencyListUseCase,
            getCurrencyUseCase: GetCurrencyUseCase,
            saveBalanceUseCase: SaveBalanceUseCase,
            addFreeConversionUseCase: AddFreeConversionUseCase
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MyWalletAppViewModel(
                    closeSplashScreen = closeSplashScreen,
                    addCurrenciesUseCase = addCurrenciesUseCase,
                    getCurrencyListUseCase = getCurrencyListUseCase,
                    getSupportedCurrencyListUseCase = getSupportedCurrencyListUseCase,
                    getCurrencyUseCase = getCurrencyUseCase,
                    saveBalanceUseCase = saveBalanceUseCase,
                    addFreeConversionUseCase = addFreeConversionUseCase
                ) as T
            }
        }
    }
}
