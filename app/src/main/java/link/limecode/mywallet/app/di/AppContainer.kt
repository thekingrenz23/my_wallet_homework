package link.limecode.mywallet.app.di

import androidx.compose.runtime.MutableState
import link.limecode.mywallet.data.local.db.MyWalletDatabase
import link.limecode.mywallet.domain.repository.BalanceRepository
import link.limecode.mywallet.domain.repository.CurrencyExchangeRateRepository
import link.limecode.mywallet.domain.repository.CurrencyRepository
import link.limecode.mywallet.domain.repository.PreferenceRepository
import link.limecode.mywallet.domain.repository.StringResourceRepository
import link.limecode.mywallet.domain.repository.SupportedCurrencyRepository

interface AppContainer {
    val isSplashScreenShown: MutableState<Boolean>
    val myWalletDatabase: MyWalletDatabase
    val currencyRepository: CurrencyRepository
    val supportedCurrencyRepository: SupportedCurrencyRepository
    val balanceRepository: BalanceRepository
    val currencyExchangeRateRepository: CurrencyExchangeRateRepository
    val stringResourceRepository: StringResourceRepository
    val preferenceRepository: PreferenceRepository
}

