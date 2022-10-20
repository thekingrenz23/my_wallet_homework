package link.limecode.mywallet.presentation.app

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import link.limecode.mywallet.app.di.AppContainer
import link.limecode.mywallet.domain.usecase.balance.GetBalanceListUseCase
import link.limecode.mywallet.domain.usecase.balance.GetBalanceUseCase
import link.limecode.mywallet.domain.usecase.balance.SaveBalanceUseCase
import link.limecode.mywallet.domain.usecase.balance.UpdateBalanceUseCase
import link.limecode.mywallet.domain.usecase.currency.GetCurrencyListUseCase
import link.limecode.mywallet.domain.usecase.currencyexchangerate.GetCurrencyExchangeRateUseCase
import link.limecode.mywallet.domain.usecase.preference.GetFreeConversionUseCase
import link.limecode.mywallet.domain.usecase.preference.UpdateFreeConversionUseCase
import link.limecode.mywallet.domain.usecase.stringresource.GetCurrencyFormatUseCase
import link.limecode.mywallet.presentation.currencyconversion.CurrencyConversionRoute
import link.limecode.mywallet.presentation.currencyconversion.CurrencyConversionViewModel
import link.limecode.mywallet.presentation.currencypicker.CurrencyPickerRoute
import link.limecode.mywallet.presentation.currencypicker.CurrencyPickerViewModel
import link.limecode.mywallet.presentation.home.HomeRoute
import link.limecode.mywallet.presentation.home.HomeViewModel

const val ANIMATION_DURATION = 300

@OptIn(ExperimentalAnimationApi::class)
@Suppress("FunctionName", "LongMethod")
@Composable
fun MyWalletAppNavigation(
    modifier: Modifier = Modifier,
    @Suppress("UnusedPrivateMember", "unused_parameter") appContainer: AppContainer,
    navController: NavHostController
) {
    AnimatedNavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Home.route,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(ANIMATION_DURATION)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(ANIMATION_DURATION)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(ANIMATION_DURATION)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(ANIMATION_DURATION)
            )
        }
    ) {
        composable(
            route = Screen.Home.route
        ) { backStackEntry ->

            val conversionMessage: String? by backStackEntry
                .savedStateHandle
                .getLiveData<String?>("conversionMessage")
                .observeAsState()

            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModel.provideFactory(
                    getBalanceListUseCase = GetBalanceListUseCase(
                        balanceRepository = appContainer.balanceRepository
                    ),
                    getFreeConversionUseCase = GetFreeConversionUseCase(
                        preferenceRepository = appContainer.preferenceRepository
                    )
                )
            )

            HomeRoute(
                viewModel = homeViewModel,
                navToCurrencyConversion = {
                    navController.navigate(Screen.ConvertCurrency.route)
                },
                backStackConversionMessage = conversionMessage,
                clearBackStackConversionMessage = {
                    backStackEntry.savedStateHandle.remove<String?>("conversionMessage")
                }
            )
        }
        composable(
            route = Screen.ConvertCurrency.route
        ) { backStackEntry ->

            val currencyTo: String? by backStackEntry
                .savedStateHandle
                .getLiveData<String?>("currencyTo")
                .observeAsState()

            val currencyConversionViewModel: CurrencyConversionViewModel = viewModel(
                factory = CurrencyConversionViewModel.provideFactory(
                    getCurrencyExchangeRateUseCase = GetCurrencyExchangeRateUseCase(
                        currencyExchangeRateRepository = appContainer.currencyExchangeRateRepository
                    ),
                    getCurrencyFormatUseCase = GetCurrencyFormatUseCase(
                        stringResourceRepository = appContainer.stringResourceRepository
                    ),
                    getFreeConversionUseCase = GetFreeConversionUseCase(
                        preferenceRepository = appContainer.preferenceRepository
                    ),
                    getBalanceUseCase = GetBalanceUseCase(
                        balanceRepository = appContainer.balanceRepository
                    ),
                    updateBalanceUseCase = UpdateBalanceUseCase(
                        balanceRepository = appContainer.balanceRepository
                    ),
                    updateFreeConversionUseCase = UpdateFreeConversionUseCase(
                        preferenceRepository = appContainer.preferenceRepository
                    ),
                    saveBalanceUseCase = SaveBalanceUseCase(
                        balanceRepository = appContainer.balanceRepository
                    )
                )
            )

            CurrencyConversionRoute(
                viewModel = currencyConversionViewModel,
                navBack = {
                    navController.popBackStack()
                },
                navToCurrencyPicker = {
                    navController.navigate(Screen.CurrencyPicker.route+"/$it")
                },
                currencyTo = currencyTo,
                clearCurrencyTo = {
                    backStackEntry.savedStateHandle.remove<String?>("currencyTo")
                },
                onConversionSuccess = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("conversionMessage", it)

                    navController.popBackStack()
                }
            )
        }
        composable(
            route = Screen.CurrencyPicker.route+"/{title}",
            arguments = listOf(
                navArgument(
                    name = "title"
                ) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""

            val currencyPickerViewModel: CurrencyPickerViewModel = viewModel(
                factory = CurrencyPickerViewModel.provideFactory(
                    getCurrencyListUseCase = GetCurrencyListUseCase(
                        currencyRepository = appContainer.currencyRepository
                    )
                )
            )

            CurrencyPickerRoute(
                viewModel = currencyPickerViewModel,
                title = title,
                navBack = {
                    navController.popBackStack()
                },
                onCurrencySelectedTo = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("currencyTo", it)

                    navController.popBackStack()
                }
            )
        }
    }
}

internal sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ConvertCurrency : Screen("convertCurrency")
    object CurrencyPicker: Screen("currencyPicker")
}
