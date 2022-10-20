package link.limecode.mywallet.presentation.currencyconversion

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.input.TextFieldValue

@Suppress("FunctionName", "LongParameterList")
@Composable
fun CurrencyConversionRoute(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    viewModel: CurrencyConversionViewModel,
    navBack: () -> Unit,
    navToCurrencyPicker: (String) -> Unit,
    currencyTo: String?,
    clearCurrencyTo: () -> Unit,
    onConversionSuccess: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    CurrencyConversionScreenSelector(
        scaffoldState = scaffoldState,
        uiState = uiState,
        navBack = navBack,
        navToCurrencyPicker = navToCurrencyPicker,
        showError = {
            viewModel.showError(it)
        },
        onErrorDismiss = {
            viewModel.errorShown(it)
        },
        reload = {
            viewModel.load(toCurrency = viewModel.initialCurrencyTo)
        },
        fromCurrencyValueChange = {
            viewModel.fromCurrencyValueChange(value = it)
        },
        toCurrencyValueChange = {
            viewModel.toCurrencyValueChange(value = it)
        },
        submit = {
            viewModel.submit()
        },
        onConversionSuccess = onConversionSuccess,
        resetOnConversionSuccess = {
            viewModel.resetIsConversionSuccessful()
        }
    )

    LaunchedEffect(currencyTo) {
        if (currencyTo != null) {
            viewModel.updateSelectedCurrencyTo(currencyTo)
            clearCurrencyTo()
        }
    }
}

@Suppress("FunctionName", "LongParameterList")
@Composable
fun CurrencyConversionScreenSelector(
    scaffoldState: ScaffoldState,
    uiState: CurrencyConversionUIState,
    navBack: () -> Unit,
    navToCurrencyPicker: (String) -> Unit,
    showError: (id: Int) -> Unit,
    onErrorDismiss: (messageId: Long) -> Unit,
    reload: () -> Unit,
    fromCurrencyValueChange: (TextFieldValue) -> Unit,
    toCurrencyValueChange: (TextFieldValue) -> Unit,
    submit: () -> Unit,
    onConversionSuccess: (String) -> Unit,
    resetOnConversionSuccess: () -> Unit
) {
    CurrencyConversionCompactScreen(
        scaffoldState = scaffoldState,
        uiState = uiState,
        navBack = navBack,
        navToCurrencyPicker = navToCurrencyPicker,
        showError = showError,
        onErrorDismiss = onErrorDismiss,
        reload = reload,
        fromCurrencyValueChange = fromCurrencyValueChange,
        toCurrencyValueChange = toCurrencyValueChange,
        submit = submit,
        onConversionSuccess = onConversionSuccess,
        resetOnConversionSuccess = resetOnConversionSuccess
    )
}
