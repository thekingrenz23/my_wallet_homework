package link.limecode.mywallet.presentation.home

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Suppress("FunctionName")
@Composable
fun HomeRoute(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    viewModel: HomeViewModel,
    navToCurrencyConversion: () -> Unit,
    backStackConversionMessage: String?,
    clearBackStackConversionMessage: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    HomeScreenSelector(
        scaffoldState = scaffoldState,
        uiState = uiState,
        navToCurrencyConversion = navToCurrencyConversion,
        backStackConversionMessage = backStackConversionMessage,
        clearBackStackConversionMessage = clearBackStackConversionMessage
    )
}

@Suppress("FunctionName")
@Composable
fun HomeScreenSelector(
    scaffoldState: ScaffoldState,
    uiState: HomeUIState,
    navToCurrencyConversion: () -> Unit,
    backStackConversionMessage: String?,
    clearBackStackConversionMessage: () -> Unit
) {
    HomeCompactScreen(
        scaffoldState = scaffoldState,
        uiState = uiState,
        navToCurrencyConversion = navToCurrencyConversion,
        backStackConversionMessage = backStackConversionMessage,
        clearBackStackConversionMessage = clearBackStackConversionMessage
    )
}
