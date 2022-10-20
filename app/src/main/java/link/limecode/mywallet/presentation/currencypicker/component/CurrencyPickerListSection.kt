package link.limecode.mywallet.presentation.currencypicker.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.emptyFlow
import link.limecode.mywallet.app.theme.MyWalletTheme
import link.limecode.mywallet.presentation.currencypicker.CurrencyPickerUIState

@Suppress("FunctionNaming")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CurrencyPickerListSection(
    modifier: Modifier,
    listState: LazyListState,
    uiState: CurrencyPickerUIState,
    onClick: (String) -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
    ) {
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            CurrencyList(
                listState = listState,
                uiState = uiState,
                onClick = onClick
            )
        }
    }
}

@Suppress("FunctionNaming")
@Preview(name = "Currency Picker List Section Light")
@Preview(name = "Currency Picker List Section Night", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewCurrencyPickerListSection() {
    MyWalletTheme {
        CurrencyPickerListSection(
            modifier = Modifier.fillMaxSize(),
            listState = rememberLazyListState(),
            uiState = CurrencyPickerUIState.IsLoaded(
                isLoading = false,
                isRefreshing = false,
                errorMessages = emptyList(),
                currencyList = emptyFlow(),
                searchTextFieldValue = TextFieldValue()
            ),
            onClick = {}
        )
    }
}
