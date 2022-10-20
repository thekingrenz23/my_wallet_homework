package link.limecode.mywallet.presentation.currencypicker

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.input.TextFieldValue

@Suppress("FunctionName")
@Composable
fun CurrencyPickerRoute(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    viewModel: CurrencyPickerViewModel,
    title: String,
    navBack: () -> Unit,
    onCurrencySelectedTo: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    CurrencyPickerScreenSelector(
        scaffoldState = scaffoldState,
        uiState = uiState,
        title = title,
        navBack = navBack,
        onSearchFieldValueChange = {
            viewModel.onSearchFieldValueChange(textFieldValue = it)
        },
        onCurrencySelectedTo = onCurrencySelectedTo
    )
}

@Suppress("FunctionName", "LongParameterList")
@Composable
fun CurrencyPickerScreenSelector(
    scaffoldState: ScaffoldState,
    uiState: CurrencyPickerUIState,
    title: String,
    navBack: () -> Unit,
    onSearchFieldValueChange: (TextFieldValue) -> Unit,
    onCurrencySelectedTo: (String) -> Unit
) {
    CurrencyPickerCompactScreen(
        scaffoldState = scaffoldState,
        uiState = uiState,
        title = title,
        navBack = navBack,
        onSearchFieldValueChange = onSearchFieldValueChange,
        onCurrencySelectedTo = onCurrencySelectedTo
    )
}

