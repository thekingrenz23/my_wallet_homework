package link.limecode.mywallet.app.di

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.room.Room
import link.limecode.mywallet.BuildConfig
import link.limecode.mywallet.data.local.db.MyWalletDatabase
import link.limecode.mywallet.data.remote.service.CurrencyExchangeRateService
import link.limecode.mywallet.data.repository.BalanceRepositoryRoomImpl
import link.limecode.mywallet.data.repository.CurrencyExchangeRateApiImpl
import link.limecode.mywallet.data.repository.CurrencyRepositoryRoomImpl
import link.limecode.mywallet.data.repository.PreferenceRepositoryRoomImpl
import link.limecode.mywallet.data.repository.StringResourceRepositoryResImpl
import link.limecode.mywallet.data.repository.SupportedCurrencyRepositoryResourceImpl
import link.limecode.mywallet.domain.repository.BalanceRepository
import link.limecode.mywallet.domain.repository.CurrencyExchangeRateRepository
import link.limecode.mywallet.domain.repository.CurrencyRepository
import link.limecode.mywallet.domain.repository.PreferenceRepository
import link.limecode.mywallet.domain.repository.StringResourceRepository
import link.limecode.mywallet.domain.repository.SupportedCurrencyRepository

class AppContainerImpl(
    val applicationContext: Context
) : AppContainer {
    override val isSplashScreenShown: MutableState<Boolean> = mutableStateOf(true)

    override val myWalletDatabase: MyWalletDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            MyWalletDatabase::class.java,
            MyWalletDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    override val currencyRepository: CurrencyRepository by lazy {
        CurrencyRepositoryRoomImpl(
            dao = myWalletDatabase.currencyDao()
        )
    }

    override val supportedCurrencyRepository: SupportedCurrencyRepository by lazy {
        SupportedCurrencyRepositoryResourceImpl(
            context = applicationContext
        )
    }

    override val balanceRepository: BalanceRepository by lazy {
        BalanceRepositoryRoomImpl(
            dao = myWalletDatabase.balanceDao()
        )
    }

    override val currencyExchangeRateRepository: CurrencyExchangeRateRepository by lazy {
        CurrencyExchangeRateApiImpl(
            currencyExchangeRateService = CurrencyExchangeRateService(
                apiUrl = BuildConfig.CURRENCY_EXCHANGE_API
            )
        )
    }

    override val stringResourceRepository: StringResourceRepository by lazy {
        StringResourceRepositoryResImpl(
            context = applicationContext
        )
    }

    override val preferenceRepository: PreferenceRepository by lazy {
        PreferenceRepositoryRoomImpl(
            dao = myWalletDatabase.preferenceDao()
        )
    }
}
