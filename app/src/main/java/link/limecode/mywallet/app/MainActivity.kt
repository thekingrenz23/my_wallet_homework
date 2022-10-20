package link.limecode.mywallet.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import link.limecode.mywallet.app.di.AppContainer
import link.limecode.mywallet.app.theme.MyWalletTheme
import link.limecode.mywallet.domain.usecase.balance.SaveBalanceUseCase
import link.limecode.mywallet.domain.usecase.currency.AddCurrenciesUseCase
import link.limecode.mywallet.domain.usecase.currency.GetCurrencyListUseCase
import link.limecode.mywallet.domain.usecase.currency.GetCurrencyUseCase
import link.limecode.mywallet.domain.usecase.preference.AddFreeConversionUseCase
import link.limecode.mywallet.domain.usecase.supportedcurrency.GetSupportedCurrencyListUseCase
import link.limecode.mywallet.presentation.app.MyWalletApp
import link.limecode.mywallet.presentation.app.MyWalletAppViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (application as MyWalletApplication).container

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                appContainer.isSplashScreenShown.value
            }
        }

        setContent {
            MyWalletTheme {
                MyWalletAppContainer(
                    appContainer = appContainer
                )
            }
        }
    }
}

@Suppress("FunctionName")
@Composable
fun MyWalletAppContainer(
    appContainer: AppContainer
) {
    /**
     * Shared View model across all pages
     */
    val myWalletAppViewModel: MyWalletAppViewModel = viewModel(
        factory = MyWalletAppViewModel.provideFactory(
            closeSplashScreen = {
                appContainer.isSplashScreenShown.value = false
            },
            addCurrenciesUseCase = AddCurrenciesUseCase(
                currencyRepository = appContainer.currencyRepository
            ),
            getCurrencyListUseCase = GetCurrencyListUseCase(
                currencyRepository = appContainer.currencyRepository
            ),
            getSupportedCurrencyListUseCase = GetSupportedCurrencyListUseCase(
                supportedCurrencyRepository = appContainer.supportedCurrencyRepository
            ),
            getCurrencyUseCase = GetCurrencyUseCase(
                currencyRepository = appContainer.currencyRepository
            ),
            saveBalanceUseCase = SaveBalanceUseCase(
                balanceRepository = appContainer.balanceRepository
            ),
            addFreeConversionUseCase = AddFreeConversionUseCase(
                preferenceRepository = appContainer.preferenceRepository
            )
        )
    )

    MyWalletApp(
        appContainer = appContainer,
        viewModel = myWalletAppViewModel
    )
}
