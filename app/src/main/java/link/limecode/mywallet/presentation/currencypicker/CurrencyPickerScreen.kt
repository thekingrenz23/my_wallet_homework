package link.limecode.mywallet.presentation.currencypicker

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.emptyFlow
import link.limecode.mywallet.app.theme.MyWalletTheme
import link.limecode.mywallet.app.util.MultipleEventsCutter
import link.limecode.mywallet.app.util.get
import link.limecode.mywallet.presentation.currencypicker.component.CurrencyPickerListSection
import link.limecode.mywallet.presentation.currencypicker.component.CurrencyPickerSearchSection

@Suppress("FunctionName", "LongParameterList")
@Composable
fun CurrencyPickerCompactScreen(
    scaffoldState: ScaffoldState,
    uiState: CurrencyPickerUIState,
    title: String,
    navBack: () -> Unit,
    onSearchFieldValueChange: (TextFieldValue) -> Unit,
    onCurrencySelectedTo: (String) -> Unit
) {
    val multipleEventsCutter = remember { MultipleEventsCutter.get() }
    val listState = rememberLazyListState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = title)
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            multipleEventsCutter.processEvent(navBack)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            CurrencyPickerSearchSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                textFieldValue = uiState.searchTextFieldValue,
                onValueChange = onSearchFieldValueChange
            )
            CurrencyPickerListSection(
                modifier = Modifier.weight(1f),
                listState = listState,
                uiState = uiState,
                onClick = {
                    multipleEventsCutter.processEvent {
                        onCurrencySelectedTo(it)
                    }
                }
            )
        }
    }
}

@Suppress("FunctionName")
@Preview(name = "Currency Picker Light")
@Preview(name = "Currency Picker Night", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewCurrencyPickerCompactScreen() {
    MyWalletTheme {
        CurrencyPickerCompactScreen(
            scaffoldState = rememberScaffoldState(),
            uiState = CurrencyPickerUIState.IsLoaded(
                isLoading = false,
                isRefreshing = false,
                errorMessages = emptyList(),
                currencyList = emptyFlow(),
                searchTextFieldValue = TextFieldValue()
            ),
            title = "Convert from",
            navBack = {},
            onSearchFieldValueChange = {},
            onCurrencySelectedTo = {}
        )
    }
}

